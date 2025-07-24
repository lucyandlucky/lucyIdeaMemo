package com.example.lucyideamemo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.ui.page.router.Screen

@Composable
fun ImageCard(note: Note, navHostController: NavHostController?) {
    if (note.attachments.size == 1) {
        AsyncImage(
            model = note.attachments[0].path,
            contentDescription = null,
            modifier = Modifier
                .width(160.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navHostController?.navigate(Screen.Main)
                },
            contentScale = ContentScale.Crop
        )
    } else {
        LazyRow(
            modifier = Modifier
                .height(90.dp)
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(
                count = note.attachments.size,
                key = { index -> note.attachments[index].path }) { index ->
                val path: String = note.attachments[index].path
                AsyncImage(
                    model = path,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .zIndex(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            navHostController?.navigate(Screen.Main)
                        },
                    contentScale = ContentScale.Crop
                )

            }

        }
    }
}