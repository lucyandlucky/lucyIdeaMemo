package com.example.lucyideamemo.ui.page.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lucyideamemo.R
import com.example.lucyideamemo.components.NoteCard
import com.example.lucyideamemo.components.RYScaffold
import com.example.lucyideamemo.state.NoteState
import com.example.lucyideamemo.ui.page.LocalMemosState
import com.example.lucyideamemo.ui.page.router.Screen
import com.example.lucyideamemo.utils.str
import com.moriafly.salt.ui.SaltTheme

@Composable
fun AllNotePage(
    navController: NavHostController,
) {
    val noteState: NoteState = LocalMemosState.current
    var openFilterBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    RYScaffold(
        title = R.string.all_note.str,
        navController = null,
        actions = {
            Toolbar(navController) {
                openFilterBottomSheet = true
            }
        },
        floatingActionButton = {
            if (!showInputDialog) {
                FloatingActionButton(
                    onClick = {
                        showInputDialog = true
                    },
                    containerColor = SaltTheme.colors.subBackground,
                    modifier = Modifier.padding(end = 16.dp, bottom = 32.dp)
                ) {
                    Icon(Icons.Rounded.Edit, stringResource(R.string.edit))
                }
            }
        }
    ) {
        Box {
            LazyColumn(Modifier.fillMaxSize()) {
                items(count = noteState.notes.size, key = { it }) { index ->
                    NoteCard(
                        noteShowBean = noteState.notes[index],
                        navHostController = navController, maxLine = 2
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
private fun Toolbar(nacController: NavHostController, filterBlock: () -> Unit) {
    IconButton(
        onClick = {
//            TODO: location list page
            nacController.navigate(route = Screen.Main) {
                launchSingleTop = true
            }
        }
    ) {
        Icon(
            contentDescription = R.string.location_info.str,
            imageVector = Icons.Outlined.LocationOn,
            tint = SaltTheme.colors.text
        )
    }

    IconButton(
        onClick = {
//            TODO: Tag List page
            nacController.navigate(route = Screen.Main) {
                launchSingleTop = true
            }
        }
    ) {
        Icon(
            contentDescription = R.string.tag.str,
            imageVector = Icons.Outlined.Tag,
            tint = SaltTheme.colors.text
        )
    }

    IconButton(
        onClick = {
//            TODO: Search page
            nacController.navigate(route = Screen.Main) {
                launchSingleTop = true
            }
        }
    ) {
        Icon(
            contentDescription = R.string.search_hint.str,
            imageVector = Icons.Outlined.Search,
            tint = SaltTheme.colors.text
        )
    }

    IconButton(
        onClick = {
//            TODO: filterBlock
            filterBlock()
        }
    ) {
        Icon(
            contentDescription = "sort",
            imageVector = Icons.Outlined.FilterList,
            tint = SaltTheme.colors.text
        )
    }

}