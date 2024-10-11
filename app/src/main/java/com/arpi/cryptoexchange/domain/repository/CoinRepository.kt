package com.arpi.cryptoexchange.domain.repository

import androidx.paging.PagingData
import com.arpi.cryptoexchange.data.local.CoinEntity
import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getAllCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto

    suspend fun getCoins(): Flow<PagingData<CoinEntity>>
}