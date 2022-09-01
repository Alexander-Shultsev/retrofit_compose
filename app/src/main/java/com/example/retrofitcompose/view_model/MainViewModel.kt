package com.example.retrofitcompose.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.model.api.RetrofitInstance
import com.example.retrofitcompose.model.api.RetrofitInstance.Companion.service
import com.example.retrofitcompose.model.api.data.Photo
import com.example.retrofitcompose.model.api.data.Post
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainViewModel : ViewModel() {

    // Статья про работу с retrofit
    // https://developer.android.com/codelabs/basic-android-kotlin-training-getting-data-internet#8

    // Посты
    private val _posts: MutableLiveData<ArrayList<Post>> = MutableLiveData()
    val posts: LiveData<ArrayList<Post>> = _posts

    fun getPosts() {
        viewModelScope.launch {
            try { _posts.value = service.getPosts().body() }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }

    // Выбранный пост
    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> = _post

    fun getPost(idPost: String) {
        viewModelScope.launch {
            try {
                _post.value = service.getPost(idPost).body()
                Log.i("TAG", "success")
            }
            catch (e: Exception) { Log.i("TAG", "getPost ---- $e") }
        }
    }

    // Список фотографий
    private val _photos: MutableLiveData<ArrayList<Photo>> = MutableLiveData()
    val photos: LiveData<ArrayList<Photo>> = _photos

    fun getPhotos() {
        viewModelScope.launch {
            try {
                _photos.value = service.getPhotos().body()
                Log.i("TAG", "success")
            }
            catch (e: Exception) { Log.i("TAG", "getPost ---- $e") }
        }
    }

    // Список пользователей
//    private val _photos: MutableLiveData<ArrayList<Photo>> = MutableLiveData()
//    val photos: LiveData<ArrayList<Photo>> = _photos
//
//    fun getPhotos() {
//        viewModelScope.launch {
//            try {
//                _photos.value = service.getPhotos().body()
//                Log.i("TAG", "success")
//            }
//            catch (e: Exception) { Log.i("TAG", "getPost ---- $e") }
//        }
//    }
}