package com.sunasterisk.music_72.data.source.remote.connection

import com.sunasterisk.music_72.data.source.remote.api.TrackService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private var trackService: TrackService? = null
    private object HOLDER {
        val INSTANCE = RetrofitClient()
    }

    fun getTrackService(): TrackService =
        trackService ?: provideRetrofit().create(TrackService::class.java).also { trackService = it }

    private fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .addInterceptor(RequestInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val READ_TIMEOUT = 5000L
        const val WRITE_TIMEOUT = 5000L
        const val CONNECT_TIMEOUT = 5000L
        const val BASE_URL = "https://api.soundcloud.com"
        val instance: RetrofitClient by lazy { HOLDER.INSTANCE }
    }
}
