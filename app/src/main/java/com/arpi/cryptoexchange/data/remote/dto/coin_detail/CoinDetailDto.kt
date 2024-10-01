package com.arpi.cryptoexchange.data.remote.dto.coin_detail

import com.arpi.cryptoexchange.domain.model.CoinDetail
import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
        val id: String,
        val symbol: String,
        val name: String,
        val web_slug: String,
        val image: Image,
        val description: Description,
        val market_cap_rank: Int,
        val market_data: MarketData,
        val additional_notices: List<Any>,
        val asset_platform_id: Any,
        val block_time_in_minutes: Int,
        val categories: List<String>,
        val country_origin: String,
        val detail_platforms: DetailPlatforms,
        val genesis_date: String,
        val hashing_algorithm: String,
        val last_updated: String,
        val links: Links,
        val platforms: Platforms,
        val preview_listing: Boolean,
        val public_notice: Any,
        val sentiment_votes_down_percentage: Double,
        val sentiment_votes_up_percentage: Double,
        val status_updates: List<Any>,
        val watchlist_portfolio_users: Int,

        )

data class MarketData(
        val current_price: CurrencyPrices,
        val ath: CurrencyPrices
)

data class CurrencyPrices(
        val aed: Double,
        val ars: Double,
        val aud: Double,
        val bch: Double,
        val bdt: Double,
        val bhd: Double,
        val bmd: Double,
        val bnb: Double,
        val brl: Double,
        val btc: Double,
        val cad: Double,
        val chf: Double,
        val clp: Double,
        val cny: Double,
        val czk: Double,
        val dkk: Double,
        val dot: Double,
        val eos: Double,
        val eth: Double,
        val eur: Double,
        val gbp: Double,
        val hkd: Double,
        val huf: Double,
        val inr: Double,
        val jpy: Double,
        val krw: Double,
        val kwd: Double,
        val ltc: Double,
        val mmk: Double,
        val mxn: Double,
        val myr: Double,
        val ngn: Double,
        val nok: Double,
        val nzd: Double,
        val php: Double,
        val pkr: Double,
        val pln: Double,
        val rub: Double,
        val sar: Double,
        val sek: Double,
        val sgd: Double,
        val thb: Double,
        @SerializedName("try") val tryy: Double,
        val twd: Double,
        val uah: Double,
        val usd: Double,
        val vef: Double,
        val vnd: Double,
        val xag: Double,
        val xau: Double,
        val xdr: Double,
        val xlm: Double,
        val xrp: Double,
        val yfi: Double,
        val zar: Double,
        val bits: Double,
        val link: Double,
        val sats: Double
)


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