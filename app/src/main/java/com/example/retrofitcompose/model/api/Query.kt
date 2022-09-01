package com.example.retrofitcompose.model.api

import android.util.Log
import com.example.retrofitcompose.model.api.data.Photo
import com.example.retrofitcompose.model.api.data.Post
import com.example.retrofitcompose.model.api.data.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface Query {

    @GET("posts")
    @Headers("Content-Type: application/json")
    suspend fun getPosts() : Response<ArrayList<Post>>

    @GET("posts/{id}")
    @Headers("Content-Type: application/json")
    suspend fun getPost(@Path("id") id: String) : Response<Post>

    @GET("photos")
    @Headers("Content-Type: application/json")
    suspend fun getPhotos() : Response<ArrayList<Photo>>

    @GET("users")
    @Headers("Content-Type: application/json")
    suspend fun getUsers() : Response<ArrayList<Users>>

}