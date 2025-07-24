package com.example.lucyideamemo.ui.page.router

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Main : Screen()
}