package com.arpi.cryptoexchange.domain.use_case.get_coin

import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading : Boolean = false,
    val coin : CoinDetail? = null,
    val error : String = ""
)