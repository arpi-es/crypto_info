package com.arpi.cryptoexchange.domain.model

import com.arpi.cryptoexchange.data.remote.dto.coin_detail.Description
import com.arpi.cryptoexchange.data.remote.dto.coin_detail.Image

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val image: Image,
    val description: String,
)
