package com.arpi.cryptoexchange.data.remote

import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {


    @GET("coins/markets")
    suspend fun getAllCoins(
        @Query("vs_currency") currency: String = "usd" , // TODO dynamic
    ): List<CoinDto>


    @GET("coins/{id}")
    suspend fun getCoinById(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = false,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false,
    ): CoinDetailDto
}
