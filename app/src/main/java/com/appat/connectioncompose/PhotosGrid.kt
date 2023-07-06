package com.appat.connectioncompose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

class Photo(
    val id: Int,
    val url: String = ""
)

@Composable
fun PhotosGrid(
    navController: NavController,
    photos: List<Photo> = List(100) { Photo(it, url = "https://picsum.photos/seed/${it+500}/128/128") }) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        contentPadding = PaddingValues(3.dp),
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos, key = { it.id }) { photo ->
            ImageItem(
                navController,
                photo
            )
        }
    }
}

@Composable
private fun ImageItem(
    navController: NavController,
    photo: Photo
) {
    Box(modifier = Modifier
        .aspectRatio(1f)
        .clickable(onClick = {
            navController.navigate("image_view/${photo.id}")
        })) {
        Image(
            painter = rememberAsyncImagePainter(photo.url),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize())
    }
}

@Composable
fun PhotoGridImageView(
    id: Int
) {
    Log.d("ID", id.toString())
    val url = "https://picsum.photos/seed/${id+500}/512/512"
    val placeHolder = rememberAsyncImagePainter(model = "https://picsum.photos/seed/${id + 500}/128/128")
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(model = url,
            contentDescription = id.toString(),
            modifier = Modifier.matchParentSize(),
            placeholder = placeHolder)
    }
}