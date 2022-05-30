package com.daniel.codetest.remote

import android.content.Context
import android.util.Log
import com.daniel.codetest.Globals
import com.daniel.codetest.R
import com.daniel.codetest.domain.model.Comments
import com.daniel.codetest.domain.model.Posts
import com.daniel.codetest.utils.CommonUtils
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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


    /**
     * Get Comments data
     */
    fun getPosts(context: Context) {
        val call: Call<Posts> = getUrl().getPosts()
        Log.d("____URL ::", call.request().url().toString())
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (!response.isSuccessful) {
                    CommonUtils.showError(
                        context,
                        context.resources.getString(R.string.network_error)
                    )
                }
                spinner.dismiss()
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                spinner.dismiss()
                CommonUtils.showError(context, context.resources.getString(R.string.network_error))
            }
        })
    }

    /**
     * Get Comments data
     */
    fun getComments(context: Context) {
        val call: Call<Comments> = getUrl().getComments()
        Log.d("____URL ::", call.request().url().toString())
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                if (!response.isSuccessful) {
                    CommonUtils.showError(
                        context,
                        context.resources.getString(R.string.network_error)
                    )
                }
                spinner.dismiss()
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                spinner.dismiss()
                CommonUtils.showError(context, context.resources.getString(R.string.network_error))
            }
        })
    }

}
