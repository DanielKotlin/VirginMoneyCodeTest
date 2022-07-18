package com.daniel.codetest.presentation.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.codetest.R
import com.daniel.codetest.domain.model.People
import com.daniel.codetest.domain.model.RoomInfo
import com.daniel.codetest.domain.model.RoomInfoItem
import com.daniel.codetest.localdb.DataInRoom
import com.daniel.codetest.localdb.PeoplesInfoListItem
import com.daniel.codetest.remote.RetrofitClient
import com.daniel.codetest.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Daniel.
 */
class HomeViewModel : ViewModel() {

    private var _allUsers = MutableLiveData<List<PeoplesInfoListItem>>()
    val allUsers: LiveData<List<PeoplesInfoListItem>> get() = _allUsers

    private var _roomInfo = MutableLiveData<List<RoomInfoItem>>()
    val roomInfo: LiveData<List<RoomInfoItem>> get() = _roomInfo

    /**
     * Get peoples data
     */
    fun getUsers(context: Context) {
        val call: Call<People> = RetrofitClient.getUrl().getPeoples()
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<People> {
            override
            fun onResponse(
                call: Call<People>,
                responseObject: Response<People>
            ) {
                if (responseObject.isSuccessful && responseObject.body() != null) {
                    val getInfoList = responseObject.body() as ArrayList<PeoplesInfoListItem>
                    insertUsersDataInRoom(context, getInfoList)
                } else {
                    CommonUtils.showError(
                        context,
                        context.resources.getString(R.string.common_error)
                    )
                }
                spinner.dismiss()
            }

            override
            fun onFailure(call: Call<People>, t: Throwable) {
                spinner.dismiss()
                CommonUtils.showError(context, context.resources.getString(R.string.common_error))
            }
        })
    }

    /**
     * Get room information
     */
    fun getRoomInfo(context: Context, id: Int) {
        val call: Call<RoomInfo> = RetrofitClient.getUrl().getRooms(id)
        val spinner = CommonUtils.showSpinner(context)
        call.enqueue(object : Callback<RoomInfo> {
            override
            fun onResponse(
                call: Call<RoomInfo>,
                responseObject: Response<RoomInfo>
            ) {
                if (responseObject.isSuccessful && responseObject.body() != null) {
                    _roomInfo.value = responseObject.body() as ArrayList<RoomInfoItem>
                } else {
                    CommonUtils.showError(
                        context,
                        context.resources.getString(R.string.common_error)
                    )
                }
                spinner.dismiss()
            }

            override
            fun onFailure(call: Call<RoomInfo>, t: Throwable) {
                spinner.dismiss()
                CommonUtils.showError(context, context.resources.getString(R.string.common_error))
            }
        })
    }

    /*
    * Insert the Users Data from Api response to Room DB
    * Getting the Data from Room and attached to MutableLiveData
    * @param context Used to get the fragment context
    * @param list List to display user data
    * */
    private fun insertUsersDataInRoom(context: Context, list: ArrayList<PeoplesInfoListItem>) {
        val roomObject = DataInRoom.getDatabase(context).infoListDataDao()
        roomObject.deleteList() //Remove this if you want to keep previous data
        roomObject.insertUsersData(list)
        // Getting the Data fro Room and add to MutableLiveData
        _allUsers.value = roomObject.getListData()
    }

    // Getting the Data from Room and attached to MutableLiveData
    fun getUsersDataFromRoom(context: Context) {
        val roomObject = DataInRoom.getDatabase(context).infoListDataDao()
        _allUsers.value = roomObject.getListData()
    }
}