package com.arpi.cryptoexchange.data.remote.dto.coin_detail

data class DetailPlatforms(
    val x : X
)

data class X(
        val contract_address: String,
        val decimal_place: Any
)