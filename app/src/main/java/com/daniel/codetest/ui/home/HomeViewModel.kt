package com.daniel.codetest.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.codetest.R
import com.daniel.codetest.domain.model.Users
import com.daniel.codetest.remote.RetrofitClient
import com.daniel.codetest.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Daniel.
 */
class HomeViewModel : ViewModel() {

    private var _allUsers = MutableLiveData<Users?>()
    val allUsers: LiveData<Users?> get() = _allUsers

    /**
     * Get users data
     */
    fun getUsers(context: Context) {
        val call: Call<Users> = RetrofitClient.getUrl().getUsers()
        Log.d("____URL ::", call.request().url().toString())
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val usersData = response.body()
                    if (usersData != null) {
                        _allUsers.value = usersData
                    }
                } else {
                    CommonUtils.showError(
                        context,
                        context.resources.getString(R.string.network_error)
                    )
                }
                spinner.dismiss()
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                spinner.dismiss()
                CommonUtils.showError(context, context.resources.getString(R.string.network_error))
            }
        })
    }
}