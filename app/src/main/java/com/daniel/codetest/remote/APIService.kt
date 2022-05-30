package com.daniel.codetest.remote

import com.daniel.codetest.domain.model.Comments
import com.daniel.codetest.domain.model.Posts
import com.daniel.codetest.domain.model.Users
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface used to provide api endpoints on implementation
 */
interface APIService {
    @GET("users")
    fun getUsers(): Call<Users>

    @GET("posts")
    fun getPosts(): Call<Posts>

    @GET("comments")
    fun getComments(): Call<Comments>

}