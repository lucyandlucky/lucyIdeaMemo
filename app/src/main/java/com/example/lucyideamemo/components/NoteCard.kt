package com.example.lucyideamemo.components

import android.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.bean.NoteShowBean
import com.example.lucyideamemo.ui.page.router.Screen
import com.example.lucyideamemo.utils.toTime
import com.moriafly.salt.ui.SaltTheme
import com.moriafly.salt.ui.Text
import dev.jeziellago.compose.markdowntext.MarkdownText

enum class NoteCardFrom {
    SEARCH, TAG_DETAIL, COMMON, SHARE
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    noteShowBean: NoteShowBean,
    navHostController: NavHostController,
    maxLine: Int,
    from: NoteCardFrom = NoteCardFrom.COMMON
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val note = noteShowBean.note

    Card(
        colors = CardDefaults.cardColors(containerColor = SaltTheme.colors.subBackground),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(CardDefaults.shape)
            .combinedClickable(
                onClick = {
                    navHostController.navigate(route = Screen.Main)
                },
                onLongClick = {
                    openBottomSheet = true
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            MarkdownText(
                markdown = note.content,
                maxLines = maxLine,
                style = SaltTheme.textStyles.paragraph.copy(fontSize = 15.sp, lineHeight = 24.sp),
                onTagClick = {
                    if (from == NoteCardFrom.COMMON) {
                        navHostController.navigate(Screen.Main)
                    }
                }
            )
            if (note.attachments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                ImageCard(note, navHostController)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LocationAndTimeText(note.createTime.toTime())
            ShowLocationInfoContent(note)
        }
    }
}

@Composable
fun LocationAndTimeText(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun ShowLocationInfoContent(note: Note?, modifier: Modifier = Modifier) {
    if (note?.locationInfo?.isBlank() == false) {
        note.locationInfo?.let {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = it,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                LocationAndTimeText(it)
            }
        }
    }
}