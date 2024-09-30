package com.arpi.cryptoexchange.data.remote

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val authToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("x-cg-demo-api-key", authToken)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}