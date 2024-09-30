package com.arpi.cryptoexchange.common

import com.arpi.cryptoexchange.BuildConfig

object Constants {


    const val BASE_URL = "https://api.coingecko.com/api/v3/"

    const val  PARAM_COIN_ID: String = "coinId"

}

object ApiConstants {
    val ACCESS_TOKEN = BuildConfig.API_KEY
}
