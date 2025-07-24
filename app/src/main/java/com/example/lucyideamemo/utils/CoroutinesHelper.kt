package com.example.lucyideamemo.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun lunchIo(runner: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.IO).launch { runner.invoke(this) }
