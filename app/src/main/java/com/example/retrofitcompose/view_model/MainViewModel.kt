package com.example.retrofitcompose.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.model.api.RetrofitInstance
import com.example.retrofitcompose.model.api.RetrofitInstance.Companion.service
import com.example.retrofitcompose.model.api.data.Post
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainViewModel : ViewModel() {

    // Товары в магазине
    private val _posts: MutableLiveData<ArrayList<Post>> = MutableLiveData()
    val posts: LiveData<ArrayList<Post>> = _posts

    fun getPosts() {
        viewModelScope.launch {
            try { _posts.value = service.getPost().body() }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }
}