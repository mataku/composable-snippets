package com.mataku.snippets.ui.compose.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Title(
  label: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = label,
    style = AppTextStyle.title,
    modifier = modifier
  )
}
