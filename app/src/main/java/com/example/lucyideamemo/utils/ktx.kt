package com.example.lucyideamemo.utils

import android.content.Context
import com.example.lucyideamemo.App
import java.util.Locale

fun Context.isSystemLanguageEnglish(): Boolean {
    val systemLanguage = Locale.getDefault().language

    return systemLanguage.startsWith("en")
}

val Int.str: String
    get() = App.instance.getString(this)