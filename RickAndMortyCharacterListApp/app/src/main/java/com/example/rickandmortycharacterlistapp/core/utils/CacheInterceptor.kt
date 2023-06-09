package com.example.rickandmortycharacterlistapp.core.utils

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}
