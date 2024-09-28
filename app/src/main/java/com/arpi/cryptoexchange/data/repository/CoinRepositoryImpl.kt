package com.arpi.cryptoexchange.data.repository

import com.arpi.cryptoexchange.data.remote.CoinApi
import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto
import com.arpi.cryptoexchange.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    val api: CoinApi,
) : CoinRepository {
    override suspend fun getAllCoins(): List<CoinDto> {
        return api.getAllCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }

}