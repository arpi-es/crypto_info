package com.arpi.cryptoexchange.data.remote.dto.coin_dto

import com.arpi.cryptoexchange.domain.model.Coin
import com.google.gson.annotations.SerializedName

data class CoinDto(

    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("market_cap") val marketCap: Long,
    @SerializedName("market_cap_rank") val marketCapRank: Int,
    @SerializedName("fully_diluted_valuation") val fullyDilutedValuation: Long?,
    @SerializedName("total_volume") val totalVolume: Long,
    @SerializedName("high_24h") val high24h: Double,
    @SerializedName("low_24h") val low24h: Double,
    @SerializedName("price_change_24h") val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double,
    @SerializedName("market_cap_change_24h") val marketCapChange24h: Double,
    @SerializedName("market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double,
    @SerializedName("circulating_supply") val circulatingSupply: Double,
    @SerializedName("total_supply") val totalSupply: Double?,
    @SerializedName("max_supply") val maxSupply: Double?,
    val ath: Double,
    @SerializedName("ath_change_percentage") val athChangePercentage: Double,
    @SerializedName("ath_date") val athDate: String,
    val atl: Double,
    @SerializedName("atl_change_percentage") val atlChangePercentage: Double,
    @SerializedName("atl_date") val atlDate: String,
    val roi: Any?,
    @SerializedName("last_updated") val lastUpdated: String
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
    )
}