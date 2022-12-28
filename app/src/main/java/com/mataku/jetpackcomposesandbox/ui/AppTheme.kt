package com.mataku.jetpackcomposesandbox.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun AppTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colors = darkColors,
    content = content
  )
}

private val darkColors = darkColors(
  primary = Color.DarkGray,
  onPrimary = Color.White,
  primaryVariant = Colors.ContentBackground,
  secondary = Color.White,
  onSecondary = Colors.textSecondary,
  surface = Color(0xFF37474F),
  onSurface = Color.White,
  background = Colors.ContentBackground,
  onBackground = Color.White
)

object Colors {
  val ContentBackground = Color(0xFF263238)
  val textSecondary = Color(0xFF90A4AE)
}

