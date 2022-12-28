package com.mataku.jetpackcomposesandbox

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog(defaultTag = "MATAKUDEBUG"))
    }
}