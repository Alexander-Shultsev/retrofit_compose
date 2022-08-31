package com.example.retrofitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitcompose.model.api.data.Post
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme
import com.example.retrofitcompose.view_model.MainViewModel

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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(mainViewModel: MainViewModel = viewModel()) {
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