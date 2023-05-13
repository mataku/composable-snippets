package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.compose.component.TagChip

@Sample(
  name = "FlowRow",
  description = "FlowRow sample",
  tags = ["FlowRow"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/LoopingHorizontalPagerScreen.kt"
)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScreen() {
  LazyColumn(
    content = {
      item {
        FlowRow(modifier = Modifier.fillMaxWidth()) {
          repeat(10) {
            TagChip(
              label = "fashion $it",
              modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
              )
          }
        }
      }
    },
    modifier = Modifier.fillMaxSize()
  )
}

@Preview
@Composable
fun FlowRowScreenPreview() {
  MaterialTheme {
    Surface {
      FlowRowScreen()
    }
  }
}
