package com.mataku.snippets

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@HiltAndroidApp
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    Napier.base(DebugAntilog(defaultTag = "MATAKUDEBUG"))
  }
}
