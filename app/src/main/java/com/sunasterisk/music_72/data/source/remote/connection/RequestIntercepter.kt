package com.sunasterisk.music_72.data.source.remote.connection

import com.sunasterisk.music_72.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().let {
            it.newBuilder().url(
                it.url.newBuilder()
                    .addQueryParameter(Constants.CLIENT_ID, Constants.API_KEY)
                    .build()
            ).build()
        })
    }
}
