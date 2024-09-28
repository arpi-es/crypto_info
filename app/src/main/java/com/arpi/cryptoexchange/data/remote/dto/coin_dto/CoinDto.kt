package com.arpi.cryptoexchange.data.remote.dto.coin_dto

import com.arpi.cryptoexchange.domain.model.Coin

data class CoinDto(
    val ath: Int,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Int,
    val current_price: Int,
    val fully_diluted_valuation: Long,
    val high_24h: Int,
    val id: String,
    val image: String,
    val last_updated: String,
    val low_24h: Int,
    val market_cap: Long,
    val market_cap_change_24h: Long,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Int,
    val max_supply: Int,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val roi: Any,
    val symbol: String,
    val total_supply: Int,
    val total_volume: Long
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap = market_cap,
        market_cap_rank = market_cap_rank,
    )
}