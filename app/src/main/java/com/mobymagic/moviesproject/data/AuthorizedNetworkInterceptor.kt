package com.mobymagic.moviesproject.data

import com.mobymagic.moviesproject.utils.HttpRequestUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Network interceptor class that adds the required parameters to the HTTP request sent to IMDb
 */
class AuthorizedNetworkInterceptor(private val apiKey: String,
                                   private val language: String,
                                   private val requestUtils: HttpRequestUtils) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain != null) {
            val originalRequest = chain.request()

            val queryParamsMap = HashMap<String, String>()
            queryParamsMap["api_key"] = apiKey
            queryParamsMap["language"] = language
            val modifiedRequest = requestUtils.addQueryParams(originalRequest, queryParamsMap)

            return chain.proceed(modifiedRequest)
        }

        return null
    }

}