package com.arpi.cryptoexchange.domain.model

import com.arpi.cryptoexchange.data.remote.dto.coin_detail.Image


val coinList = listOf(
        Coin(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                image = "https://coin-images.coingecko.com/coins/images/1/thumb/bitcoin.png?1696501400",
                currentPrice = 27350.45,
                marketCap = 512034567890L,
                marketCapRank = 1,

                ),
        Coin(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                currentPrice = 1850.70,
                marketCap = 223504567890L,
                marketCapRank = 2,
                image = "https://assets.coingecko.com/coins/images/279/large/ethereum.png"
        )
)

val coinDetail = CoinDetail(

        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = Image( large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1696501400" ,
                thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png?1696501400") ,
        marketDataPrice = 256486.0,
        marketCapRank = 1,
        description = "Bitcoin is the first successful internet money based on peer-to-peer technology..."
)
