package com.example.retrofitcompose.model.api

import android.util.Log
import com.example.retrofitcompose.model.api.data.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Query {

    @GET("posts")
    @Headers("Content-Type: application/json")
    suspend fun getPost() : Response<ArrayList<Post>>

}