package com.example.retrofitcompose

import android.content.pm.ModuleInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitcompose.model.api.data.Photo
import com.example.retrofitcompose.model.api.data.Post
import com.example.retrofitcompose.ui.component.ImageView
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme
import com.example.retrofitcompose.view_model.MainViewModel
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf(0) }

    LazyColumn {
        stickyHeader {
            LazyRow {
                item {
                    Button(onClick = { currentScreen = 1 }) {
                        Text(text = "Все посты")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 2 }) {
                        Text(text = "Один пост")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 3 }) {
                        Text(text = "Изображения")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 4 }) {
                        Text(text = "Пользователи")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 5 }) {
                        Text(text = "Все посты POST запрос")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 6 }) {
                        Text(text = "Обновление поста PUT")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 7 }) {
                        Text(text = "Обновление поста PATCH")
                    }
                }
                item {
                    Button(onClick = { currentScreen = 8 }) {
                        Text(text = "Удаление поста")
                    }
                }
            }
        }
        item {
            when (currentScreen) {
                1 -> GetAllPost(mainViewModel)
                2 -> GetOnePost(mainViewModel)
                3 -> GetImage(mainViewModel)
                4 -> GetAllUsers(mainViewModel)
                5 -> GetAllPost2(mainViewModel)
                6 -> PutPost(mainViewModel)
                7 -> PatchPost(mainViewModel)
                8 -> DeletePost(mainViewModel)
            }
        }
    }
}

@Composable
fun DeletePost(mainViewModel: MainViewModel) {
    mainViewModel.deletePost("3")
    val posts3 by mainViewModel.deletePosts.observeAsState("")

    Column {
        Text(text = "Status: $posts3")
    }
}

@Composable
fun PatchPost(mainViewModel: MainViewModel) {
    mainViewModel.patchPost("3", "Day of brain")
    val posts3 by mainViewModel.patchPost.observeAsState(Post(0, 0, "", ""))

    Column {
        Text(text = posts3.id.toString())
        Text(text = posts3.userId.toString())
        Text(text = posts3.title)
        Text(text = posts3.body)
    }
}

@Composable
fun PutPost(mainViewModel: MainViewModel) {
    mainViewModel.putPost("1", "1", "Day of brain", "wow holiday")
    val posts3 by mainViewModel.putPosts.observeAsState(Post(0, 0, "", ""))

    Column {
        Text(text = posts3.id.toString())
        Text(text = posts3.userId.toString())
        Text(text = posts3.title)
        Text(text = posts3.body)
    }
}

@Composable
fun GetAllPost2(mainViewModel: MainViewModel) {
    mainViewModel.postPost("1", "Day of brain", "wow holiday")
    val posts2 by mainViewModel.posts2.observeAsState(Post(0, 0, "", ""))

    Column {
        Text(text = posts2.id.toString())
        Text(text = posts2.userId.toString())
        Text(text = posts2.title)
        Text(text = posts2.body)
    }
}

@Composable
fun GetAllUsers(mainViewModel: MainViewModel) {
    mainViewModel.getUsers()
    val users by mainViewModel.users.observeAsState(ArrayList())

    Column {
        for (elem in users) {
            LazyRow {
                item { Text(elem.address.city) }
                item { Text(elem.address.street) }
                item { Text(elem.address.street) }
                item { Text(elem.address.suite) }
            }
            LazyRow {
                item { Text(elem.company.name) }
                item { Text(elem.company.catchPhrase) }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun GetImage(mainViewModel: MainViewModel) {
    mainViewModel.getPhotos()
    val photos by mainViewModel.photos.observeAsState(ArrayList())

    Column {
        for (elem in photos) {
            ImageView(
                imageUrl = elem.url,
                modifier = Modifier
                    .size(100.dp))
            Text(elem.title)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun GetOnePost(mainViewModel: MainViewModel) {
    var numberPost by remember { mutableStateOf("1") }
    val post by mainViewModel.post.observeAsState(Post(0, 0, "", ""))

    Column {
        TextField(
            value = numberPost,
            onValueChange = {
                numberPost = it
                if (numberPost != "") {
                    if (numberPost.toInt() in 1..100) {
                        mainViewModel.getPost(numberPost)
                    }
                }
            }
        )
        Text(text = post.title)
        Text(text = post.body)
    }
}


@Composable
fun GetAllPost(mainViewModel: MainViewModel) {
    mainViewModel.getPosts()
    val posts by mainViewModel.posts.observeAsState(ArrayList())

    Column {
        for (elem in posts) {
            Text(text = elem.id.toString())
            Text(text = elem.userId.toString())
            Text(text = elem.title)
            Text(text = elem.body)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}