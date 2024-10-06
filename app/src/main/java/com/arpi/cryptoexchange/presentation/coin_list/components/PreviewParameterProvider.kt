package com.arpi.cryptoexchange.presentation.coin_list.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.model.coinDetail
import com.arpi.cryptoexchange.domain.model.coinList
import com.arpi.cryptoexchange.presentation.coin_detail.CoinDetailState
import com.arpi.cryptoexchange.presentation.coin_list.CoinListState

class CoinPreviewProvider : PreviewParameterProvider<Coin> {

    override val values = sequenceOf(
            coinList[0]
    )
}


class CoinListPreviewProvider : PreviewParameterProvider<List<Coin>> {

    override val values = sequenceOf(
        coinList
    )
}

class CoinListStateProvider : PreviewParameterProvider<CoinListState> {
    override val values = sequenceOf(
        CoinListState(isLoading = true, coins = emptyList(), error = ""),
        CoinListState(isLoading = false, coins = coinList, error = ""),
        CoinListState(isLoading = false, coins = emptyList(), error = "Unable to load data")
    )
}


class CoinDetailStateProvider : PreviewParameterProvider<CoinDetailState> {
    override val values = sequenceOf(
        CoinDetailState(isLoading = true, coin = null, error = ""),
        CoinDetailState(isLoading = false, coin = coinDetail, error = ""),
        CoinDetailState(isLoading = false, coin = null, error = "Unable to load data")
    )
}





