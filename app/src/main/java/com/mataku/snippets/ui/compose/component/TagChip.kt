package com.mataku.snippets.ui.compose.component

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
      containerColor = if (selected) {
        MaterialTheme.colorScheme.onSurface
      } else {
        MaterialTheme.colorScheme.surface
      },
    )
  ) {
    Text(
      text = label,
      modifier = Modifier,
      color = if (selected) {
        MaterialTheme.colorScheme.surface
      } else {
        MaterialTheme.colorScheme.onSurface
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
