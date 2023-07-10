package com.mataku.snippets.ui.compose.component

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// TODO: replace with Material Chip
@Composable
fun TagChip(
  label: String,
  selected: Boolean,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {}
) {
  Button(
    onClick = onClick,
    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = if (selected) {
        MaterialTheme.colors.onSurface
      } else {
        MaterialTheme.colors.surface
      }
    )
  ) {
    Text(
      text = label,
      modifier = Modifier,
      color = if (selected) {
        MaterialTheme.colors.surface
      } else {
        MaterialTheme.colors.onSurface
      }
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun TagChipPreview() {
  MaterialTheme {
    Surface {
      TagChip(label = "プレイリスト", selected = false)
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun TagChipSelectedPreview() {
  MaterialTheme {
    Surface {
      TagChip(label = "プレイリスト", selected = true)
    }
  }
}
