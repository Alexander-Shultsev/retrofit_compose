package com.example.retrofitcompose.model.api

import com.example.retrofitcompose.model.api.data.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Query {

    @GET("posts")
    @Headers("Content-Type: application/json")
    fun getPost() : Call<ArrayList<Post>>

}