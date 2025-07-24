package com.example.lucyideamemo.utils

import com.example.lucyideamemo.App

val Int.str: String
    get() = App.instance.getString(this)