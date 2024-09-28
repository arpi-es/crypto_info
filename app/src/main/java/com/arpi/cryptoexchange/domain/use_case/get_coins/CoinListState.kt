package com.arpi.cryptoexchange.domain.use_case.get_coins

import com.arpi.cryptoexchange.domain.model.Coin

data class CoinListState(
    val isLoading : Boolean = false,
    val coins : List<Coin> = emptyList(),
    val error : String = ""
)