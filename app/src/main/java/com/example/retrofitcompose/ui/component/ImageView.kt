package com.example.retrofitcompose.ui.component

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

sealed class ImageErrorStatus {
    object NoImageProvider: ImageErrorStatus()
    object NoImageLoaded: ImageErrorStatus()
    data class RemoteError(val cause: Exception?): ImageErrorStatus()
}

sealed class ImageViewState {
    object Loading: ImageViewState()
    data class Display(val image: Bitmap): ImageViewState()
    data class Error(val status: ImageErrorStatus?): ImageViewState()
}

@Composable
fun ImageView(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier.fillMaxSize(),
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    loader: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
) {
    val imageViewState = loadImage(url = imageUrl)

    Box(modifier = modifier) {
        when (val state = imageViewState.value) {
            ImageViewState.Loading -> loader?.let { it() }
                ?: Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.LightGray))
            is ImageViewState.Display -> {
                Image(
                    bitmap =  state.image.asImageBitmap(),
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = imageModifier
                )
            }
            is ImageViewState.Error -> error?.let { it() }
                ?: Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error: " + when (state.status) {
                        ImageErrorStatus.NoImageProvider -> "Url empty"
                        ImageErrorStatus.NoImageLoaded -> "Can`t load image"
                        is ImageErrorStatus.RemoteError -> "Custom ${state.status.cause?.localizedMessage}"
                        else -> {}
                    }
                )
        }
    }
}

@Composable
fun loadImage(url: String?): MutableState<ImageViewState> {
    val imageViewState: MutableState<ImageViewState> = remember(url) {
        mutableStateOf(ImageViewState.Loading)
    }

    if (url.isNullOrBlank()) {
        imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageProvider)
    } else {
        Picasso.get().load(url).into(object: Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val image = bitmap ?: run {
                    imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageLoaded)
                    return@onBitmapLoaded
                }

                imageViewState.value = ImageViewState.Display(image)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                e?.printStackTrace()
                imageViewState.value = ImageViewState.Error(ImageErrorStatus.RemoteError(e))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }

    return imageViewState
}
