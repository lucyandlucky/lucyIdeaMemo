package com.example.lucyideamemo.ui.page.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.util.TableInfo
import com.example.lucyideamemo.ui.page.home.AllNotePage
import com.moriafly.salt.ui.Text
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(navController: NavHostController) {

    var currentDestination by rememberSaveable { mutableStateOf(NavigationBarPath.AllNote.route) }
    val hideNavBar by rememberSaveable { mutableStateOf(false) }
    val pageState =
        rememberPagerState(initialPage = 0, pageCount = { NavigationBarPath.entries.size })
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isWideScreen = remember(configuration.orientation) { false }

    val navigationBar: @Composable () -> Unit = {
        AdaptNavigationBar(
            destinations = NavigationBarPath.entries,
            currentDestination = currentDestination,
            onNavigateToDestination = {
                currentDestination = NavigationBarPath.entries[it].route
                scope.launch { pageState.scrollToPage(it) }
            },
            isWideScreen = isWideScreen
        )
    }

    val pagerContent: @Composable (Modifier) -> Unit = { modifier ->
        HorizontalPager(
            state = pageState,
            userScrollEnabled = false,
            modifier = modifier
        ) { page ->
            when (page) {
                0 -> AllNotePage(navController = navController)
                1 -> Text("note")
                2 -> Text("setting")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        pagerContent(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        navigationBar()
    }

}