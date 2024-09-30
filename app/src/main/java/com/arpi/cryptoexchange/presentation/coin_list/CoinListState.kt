package com.arpi.cryptoexchange.presentation.coin_list

import com.arpi.cryptoexchange.domain.model.Coin

data class CoinListState(
    val isLoading : Boolean = false,
    val coins : List<Coin> = emptyList(),
    val error : String = ""
)