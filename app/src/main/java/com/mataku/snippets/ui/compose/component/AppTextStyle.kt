package com.mataku.snippets.ui.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyle {
  val title: TextStyle
    @Composable
    get() = TextStyle.Default.copy(
      fontSize = 20.sp,
      fontWeight = FontWeight.Medium,
    )
}

