package com.mataku.jetpackcomposesandbox.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.mataku.jetpackcomposesandbox.ui.AppTheme
import com.mataku.jetpackcomposesandbox.ui.compose.MainScreen

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AppTheme {
        Surface {
          MainScreen()
        }
      }
    }
  }
}
