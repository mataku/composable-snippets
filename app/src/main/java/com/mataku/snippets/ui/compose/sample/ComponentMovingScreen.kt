package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample

@Sample(
  name = "Position Moving",
  description = "Position Moving by tapping and dragging",
  tags = ["PointerInput", "Tap", "Drag"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/ComponentMovingScreen.kt"
)
@Composable
fun ComponentMovingScreen() {
  ComponentMovingContent()
}

@Composable
private fun ComponentMovingContent(
  modifier: Modifier = Modifier,
) {
  var tapPosition by remember {
    mutableStateOf(Offset(0f, 0f))
  }
  var dragPosition by remember {
    mutableStateOf(Offset(0f, 0f))
  }
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Detect Tapping",
      fontSize = 20.sp,
      modifier = Modifier
        .padding(
          vertical = 12.dp
        )
    )

    Box(
      modifier = modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
          detectTapGestures { offset ->
            tapPosition = offset
          }
        }
        .weight(1F)
    ) {
      TapMovingAnimation(
        position = tapPosition
      )
    }

    Divider()

    Text(
      text = "Detect Dragging",
      fontSize = 20.sp,
      modifier = Modifier
        .padding(
          vertical = 12.dp
        )
    )

    Box(
      modifier = modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
          detectDragGestures { change, dragAmount ->
            change.consume()
            dragPosition += dragAmount
          }
        }
        .weight(1F)
    ) {
      DraggingMovingComponent(
        position = dragPosition
      )
    }
  }
}

@Composable
private fun TapMovingAnimation(
  position: Offset,
) {
  val size = 20.dp
  val density = LocalDensity.current
  Box(
    modifier = Modifier
      .offset {
        IntOffset(
          x = with(density) {
            position.x.toInt() - (size.toPx() / 2).toInt()
          },
          y = with(density) {
            position.y.toInt() - (size.toPx() / 2).toInt()
          }
        )
      }
      .background(
        color = Color(0xFFD4E157)
      )
      .size(size)
  )
}

@Composable
private fun DraggingMovingComponent(
  position: Offset,
) {
  val size = 20.dp
  val density = LocalDensity.current
  Box(
    modifier = Modifier
      .offset {
        IntOffset(
          x = with(density) {
            position.x.toInt() - (size.toPx() / 2).toInt()
          },
          y = with(density) {
            position.y.toInt() - (size.toPx() / 2).toInt()
          }
        )
      }
      .background(
        color = Color(0xFF00897B)
      )
      .size(size)
  )
}

@Composable
@Preview(showBackground = true)
private fun ComponentMovingScreenPreview() {
  MaterialTheme {
    Surface {
      ComponentMovingContent()
    }
  }
}


