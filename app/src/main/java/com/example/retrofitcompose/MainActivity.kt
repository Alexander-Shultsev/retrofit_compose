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
import com.example.retrofitcompose.model.api.data.Post
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
                    Button(onClick = { currentScreen = 0 }) {
                        Text(text = "Все посты")
                    }
                }

                item {
                    Button(onClick = { currentScreen = 1 }) {
                        Text(text = "Один пост")
                    }
                }
            }
        }
        item {
            when (currentScreen) {
                0 -> getAllPost(mainViewModel)
                1 -> getOnePost(mainViewModel)
            }
        }
    }
}

@Composable
fun getOnePost(mainViewModel: MainViewModel) {
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
//        Button(onClick = {
//            mainViewModel.getPost(numberPost)
//        }) {
//            Text(text = "Найти")
//        }

        Text(text = post.title)
        Text(text = post.body)
    }
}


@Composable
fun getAllPost(mainViewModel: MainViewModel) {
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