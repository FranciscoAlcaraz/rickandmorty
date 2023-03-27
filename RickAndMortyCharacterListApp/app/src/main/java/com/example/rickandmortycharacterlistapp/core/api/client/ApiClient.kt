package com.example.rickandmortycharacterlistapp.core.api.client

import com.example.rickandmortycharacterlistapp.App
import com.example.rickandmortycharacterlistapp.core.utils.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private const val CACHE_SIZE: Long = 10 * 1024 * 1024

    fun getClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CacheInterceptor())
            .cache(Cache(App.instance.cacheDir, CACHE_SIZE))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
