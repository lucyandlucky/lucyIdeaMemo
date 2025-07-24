package com.example.lucyideamemo.ui.page.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.lucyideamemo.state.NoteState
import com.example.lucyideamemo.ui.page.LocalMemosState
import com.example.lucyideamemo.ui.page.LocalNoteViewModel
import com.example.lucyideamemo.ui.page.NoteViewModel
import com.example.lucyideamemo.ui.page.router.App
import com.example.lucyideamemo.utils.FirstTimeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firstTimeManager: FirstTimeManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()


        firstTimeManager.generateIntroduceNote()

        lifecycleScope.launch {
            setContent {
                SettingsProvider {
                    App()
                }
            }
        }
    }

    @Composable
    fun SettingsProvider(
        noteViewModel: NoteViewModel = hiltViewModel(),
        content: @Composable () -> Unit
    ) {
        val state: NoteState by noteViewModel.state.collectAsState(Dispatchers.IO)

        CompositionLocalProvider(
            LocalNoteViewModel provides noteViewModel,
            LocalMemosState provides state
        ) {
            content()
        }
    }
}
