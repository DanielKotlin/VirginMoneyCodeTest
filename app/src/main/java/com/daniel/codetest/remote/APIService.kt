package com.daniel.codetest.remote

import com.daniel.codetest.domain.model.People
import com.daniel.codetest.domain.model.RoomInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface used to provide api endpoints on implementation
 */
interface APIService {

    @GET("people")
    fun getPeoples(): Call<People>

    @GET("rooms?")
    fun getRooms(
        @Query("id") page: Int,
    ): Call<RoomInfo>
}