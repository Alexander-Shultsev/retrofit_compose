package com.example.retrofitcompose.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitcompose.model.api.RetrofitInstance.Companion.service
import com.example.retrofitcompose.model.api.data.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    // Товары в магазине
    private val _posts: MutableLiveData<Post> = MutableLiveData()
    val posts: LiveData<Post> = _posts

    fun getPosts() {
        service.getPost().enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) _posts.postValue(response.body())
                Log.i("TAG", "Удачно $call")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.i("TAG", "Не удачно $call $t")
            }
        })
    }

}