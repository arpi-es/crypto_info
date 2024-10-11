package com.arpi.cryptoexchange.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arpi.cryptoexchange.data.local.CoinDatabase
import com.arpi.cryptoexchange.data.local.CoinEntity
import com.arpi.cryptoexchange.data.local.RemoteKeys
import com.arpi.cryptoexchange.data.mapper.toCoinEntity
import com.arpi.cryptoexchange.data.remote.CoinApi
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class GetCoinsMediator(
    private val api: CoinApi,
    private val database: CoinDatabase
) : RemoteMediator<Int, CoinEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                // Determine the page to load for a refresh (start from the first page if no keys are found)
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true) // No prepending supported
            LoadType.APPEND -> {

                // Fetch the remote key for the last item (for loading more data)
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey

            }
        }

        try {
            val apiResponse = api.getAllCoins(page, state.config.pageSize) // Request next page from API
            val coins = apiResponse.map { it.toCoinEntity() } // Map API response to your entity

            // If the API returns an empty list, assume end of pagination
            val endOfPaginationReached = coins.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // Clear old data for a refresh
                    database.remoteKeysDao.clearRemoteKeys()
                    database.coinDao.clearAll()
                }

                // Insert new data into the database
                val keys = apiResponse.map { coin ->
                    RemoteKeys(
                        coinId = coin.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPaginationReached) null else page + 1
                    )
                }
                database.remoteKeysDao.insertAll(keys)
                database.coinDao.upsertAll(coins)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    // Helper function to get the closest RemoteKey to the current position
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CoinEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao.remoteKeysCoinId(id)
            }
        }
    }

    // Helper function to get the RemoteKey for the first item
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CoinEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { coin ->
            database.remoteKeysDao.remoteKeysCoinId(coin.id)
        }
    }

    // Helper function to get the RemoteKey for the last item
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CoinEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { coin ->
            database.remoteKeysDao.remoteKeysCoinId(coin.id)
        }
    }

}