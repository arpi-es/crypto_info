package com.arpi.cryptoexchange.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arpi.cryptoexchange.common.Constants
import com.arpi.cryptoexchange.data.local.CoinDatabase
import com.arpi.cryptoexchange.data.local.CoinEntity
import com.arpi.cryptoexchange.data.remote.CoinApi
import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto
import com.arpi.cryptoexchange.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi,
    private val database: CoinDatabase
) : CoinRepository {
    override suspend fun getAllCoins(): List<CoinDto> {
        return api.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }

    override suspend fun getCoins(): Flow<PagingData<CoinEntity>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            remoteMediator = GetCoinsMediator(api, database),
            pagingSourceFactory = { database.coinDao.pagingSource() }
        ).flow
    }


}