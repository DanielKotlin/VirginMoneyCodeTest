package com.daniel.codetest.ui.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.codetest.R
import com.daniel.codetest.domain.model.Posts
import com.daniel.codetest.remote.RetrofitClient
import com.daniel.codetest.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Daniel.
 */
class PostViewModel : ViewModel() {

    private var _allPosts = MutableLiveData<Posts?>()
    val allPosts: LiveData<Posts?> get() = _allPosts

    /**
     * Get Posts data
     */
    fun getPosts(context: Context) {
        val call: Call<Posts> = RetrofitClient.getUrl().getPosts()
        Log.d("____URL ::", call.request().url().toString())
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (response.isSuccessful) {
                    val postData = response.body()
                    if (postData != null) {
                        _allPosts.value = postData
                    }
                } else {
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

}