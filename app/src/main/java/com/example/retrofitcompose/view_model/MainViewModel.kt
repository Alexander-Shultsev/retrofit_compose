package com.example.retrofitcompose.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.model.api.RetrofitInstance
import com.example.retrofitcompose.model.api.RetrofitInstance.Companion.service
import com.example.retrofitcompose.model.api.data.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainViewModel : ViewModel() {

    // Статья про работу с retrofit
    // https://developer.android.com/codelabs/basic-android-kotlin-training-getting-data-internet#8

    // Список постов
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
    private val _users: MutableLiveData<ArrayList<Users>> = MutableLiveData()
    val users: LiveData<ArrayList<Users>> = _users

    fun getUsers() {
        viewModelScope.launch {
            try {
                _users.value = service.getUsers().body()
                Log.i("TAG", "success")
            }
            catch (e: Exception) { Log.i("TAG", "getPost ---- $e") }
        }
    }

    // Добавление поста
    private val _posts2: MutableLiveData<Post> = MutableLiveData()
    val posts2: LiveData<Post> = _posts2

    fun postPost(userId: String, title: String, body: String) {
        val body = Post2(userId, title, body)

        viewModelScope.launch {
            try { _posts2.value = service.postPost(body).body() }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }

    // Обновление поста PUT (все данные)
    private val _putPosts: MutableLiveData<Post> = MutableLiveData()
    val putPosts: LiveData<Post> = _putPosts

    fun putPost(id: String, userId: String, title: String, body: String) {
        viewModelScope.launch {
            try { _putPosts.value = service.putPost(id, userId, title, body).body() }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }

    // Обновление поста PATCH (часть данных)
    private val _patchPosts: MutableLiveData<Post> = MutableLiveData()
    val patchPost: LiveData<Post> = _patchPosts

    fun patchPost(id: String, title: String) {
        viewModelScope.launch {
            try { _patchPosts.value = service.patchPost(id, title).body() }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }

    // Удаление поста
    private val _deletePosts: MutableLiveData<String> = MutableLiveData()
    val deletePosts: LiveData<String> = _deletePosts

    fun deletePost(id: String) {
        viewModelScope.launch {
            try {
                val response = service.deletePost(id)
                _deletePosts.value = response.code().toString()
            }
            catch (e: Exception) { Log.i(TAG, "getPost2: $e") }
        }
    }
}