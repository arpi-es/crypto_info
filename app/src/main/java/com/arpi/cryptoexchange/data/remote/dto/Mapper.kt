package com.arpi.cryptoexchange.data.remote.dto

import com.arpi.cryptoexchange.data.remote.dto.coin_detail.CoinDetailDto
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.CoinDto
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.model.CoinDetail

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

fun CoinDetailDto.toCoinDetail(): CoinDetail {

    return CoinDetail(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        marketCapRank = market_cap_rank,
        marketDataPrice = market_data.current_price.usd,
        description = description.en
    )

}