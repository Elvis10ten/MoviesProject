package com.mobymagic.moviesproject.utils

import okhttp3.Request

class HttpRequestUtils {

    fun addQueryParams(originalRequest: Request, queryParamsMap: Map<String, String>): Request {
        val originalHttpUrl = originalRequest.url()
        val httpUrlBuilder = originalHttpUrl.newBuilder()

        for ((key, value) in queryParamsMap) {
            httpUrlBuilder.addQueryParameter(key, value)
        }

        val httpUrl = httpUrlBuilder.build()
        val requestBuilder = originalRequest.newBuilder().url(httpUrl)

        return requestBuilder.build()
    }

}