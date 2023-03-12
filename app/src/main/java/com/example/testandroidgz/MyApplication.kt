package com.example.testandroidgz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MyApplication : Application() {
    val mApplicationScope = CoroutineScope(SupervisorJob())
}