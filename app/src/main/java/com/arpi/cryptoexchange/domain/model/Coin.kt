package com.arpi.cryptoexchange.domain.model

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Int,
    val market_cap: Long,
    val market_cap_rank: Int,
)
