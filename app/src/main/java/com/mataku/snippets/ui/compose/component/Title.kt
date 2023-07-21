package com.mataku.snippets.ui.compose.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Title(
  label: String,
  modifier: Modifier = Modifier,
  textAlign: TextAlign? = null
) {
  Text(
    text = label,
    style = AppTextStyle.title,
    modifier = modifier,
    textAlign = textAlign
  )
}
