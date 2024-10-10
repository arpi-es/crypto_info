package com.arpi.cryptoexchange.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arpi.cryptoexchange.data.local.CoinDatabase
import com.arpi.cryptoexchange.data.local.CoinEntity
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
            LoadType.REFRESH -> 1 // Start from page 1 when refreshing
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true) // No prepending supported
            LoadType.APPEND -> {
                val lastPage = getLastPage(state)

                if (lastPage == null) {
                    return MediatorResult.Success(endOfPaginationReached = true) // If we can't get a valid page number, stop
                }
                lastPage + 1 // Increment the page for loading more data
            }
        }

        try {
            val apiResponse = api.getAllCoins(page, state.config.pageSize) // Request next page from API
            val coins = apiResponse.map { it.toCoinEntity() } // Map API response to your entity

            // If the API returns an empty list, assume end of pagination
            val endOfPaginationReached = coins.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.coinDao.clearAll() // Clear old data on refresh
                }
                database.coinDao.upsertAll(coins) // Insert the new coins
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    // Helper function to get the last page from the PagingState
    private fun getLastPage(state: PagingState<Int, CoinEntity>): Int? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.size?.let {
            // This could be derived from any suitable property like list size or position
            it / state.config.pageSize
        }
    }


}