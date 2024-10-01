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