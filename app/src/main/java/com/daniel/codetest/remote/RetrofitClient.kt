package com.daniel.codetest.remote

import com.daniel.codetest.Globals
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Daniel.
 */
object RetrofitClient {

    private var retrofit: Retrofit? = null
    private fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())/* Converter Factory to convert your Json to gson */
                .build()
        }
        return retrofit!!
    }

    fun getUrl(): APIService {
        return getClient().create(APIService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }

}
