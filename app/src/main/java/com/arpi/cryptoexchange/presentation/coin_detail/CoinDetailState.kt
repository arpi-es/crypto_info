package com.arpi.cryptoexchange.presentation.coin_detail

import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading : Boolean = false,
    val coin : CoinDetail? = null,
    val error : String = ""
)