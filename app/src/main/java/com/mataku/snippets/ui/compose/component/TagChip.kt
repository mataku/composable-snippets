package com.mataku.snippets.ui.compose.component

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TagChip(
  label: String,
  modifier: Modifier = Modifier
) {
  OutlinedButton(
    onClick = {},
    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    modifier = modifier
  ) {
    Text(text = label)
  }
}

@Preview(showBackground = true)
@Composable
private fun TagChipPreview() {
  MaterialTheme {
    Surface {
      TagChip(label = "ファッション")
    }
  }
}
