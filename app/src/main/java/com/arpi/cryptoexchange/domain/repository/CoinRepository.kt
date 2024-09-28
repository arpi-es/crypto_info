package com.arpi.cryptoexchange.domain.repository

import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto

interface CoinRepository {


    suspend fun getAllCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}