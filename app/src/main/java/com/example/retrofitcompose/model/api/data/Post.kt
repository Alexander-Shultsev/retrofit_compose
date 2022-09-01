package com.example.retrofitcompose.model.api.data

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class Post2 (
    val userId: String,
    val title: String,
    val body: String
)
