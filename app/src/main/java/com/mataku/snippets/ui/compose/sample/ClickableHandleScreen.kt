@file:OptIn(ExperimentalFoundationApi::class)

package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.catalog.framework.annotations.Sample
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@Sample(
  name = "Clickable handle sample",
  description = "Clickable handle sample",
  tags = ["clickable", "throttle"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/ClickableHandleScreen.kt"
)
@Composable
fun ClickableHandleScreen() {
  val state = rememberClickableHookState()
  val logList by state.log.collectAsStateWithLifecycle()

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Click!!!!!!!!!!!!",
      modifier = Modifier
        .throttleClickable(
          throttleTimeMs = 1000L,
          onClick = {
            state.addLog()
          },
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple()
        )
        .padding(
          horizontal = 16.dp,
          vertical = 8.dp
        ),
      fontSize = 16.sp
    )
    Spacer(
      Modifier.height(16.dp)
    )
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      contentPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 16.dp
      ),
    ) {
      stickyHeader(
        key = "log_header"
      ) {
        Text(
          text = "Click Log History",
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              vertical = 8.dp
            ),
          fontWeight = FontWeight.Medium,
          fontSize = 16.sp,
        )
      }
      items(logList) { log ->
        Cell(log = log)
      }
    }
  }
}


@Composable
fun rememberClickableHookState(): ClickableHookState {
  return remember {
    ClickableHookState()
  }
}

@Composable
private fun Cell(
  log: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = log,
    modifier = modifier
      .padding(
        horizontal = 12.dp,
        vertical = 8.dp
      ),
    color = MaterialTheme.colors.onSurface
  )
}

class ClickableHookState {
  private val _log: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

  val log: MutableStateFlow<List<String>> = _log

  fun addLog() {
    _log.update {
      it + "Clicked!!!: ${Date()}"
    }
  }
}


class ThrottleClickableNode(
  var throttleTime: Long,
  var onClick: () -> Unit,
  var interactionSource: MutableInteractionSource?,
) : PointerInputModifierNode, Modifier.Node() {
  private var invokable = true
  private var lastPress: PressInteraction.Press? = null

  override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) {
    if (invokable) {
      when (pointerEvent.type) {
        PointerEventType.Press -> {
          if (pass == PointerEventPass.Initial) {
            val press = PressInteraction.Press(pointerEvent.changes.last().position)
            interactionSource?.tryEmit(press)
            lastPress = press
          }
        }

        PointerEventType.Release -> {
          if (pass == PointerEventPass.Final) {
            val lastChange = pointerEvent.changes.lastOrNull() ?: return
            if (invokable && lastPress != null) {
              invokable = false
              coroutineScope.launch {
                delay(throttleTime)
                invokable = true
              }
              if (positionWithinBounds(lastChange.position, bounds)) {
                onClick.invoke()
              }
              interactionSource?.tryEmit(
                PressInteraction.Release(lastPress!!)
              )
              lastPress = null
            }
          }
        }

        PointerEventType.Move -> {
          if (pass == PointerEventPass.Final && lastPress != null) {
            val lastChange = pointerEvent.changes.lastOrNull() ?: return
            if (!positionWithinBounds(lastChange.position, bounds)) {
              interactionSource?.tryEmit(
                PressInteraction.Release(lastPress!!)
              )
              lastPress = null
            }
          }
        }
      }
    }
  }

  override fun onCancelPointerInput() {
    lastPress = null
    invokable = true
  }
}

private fun positionWithinBounds(
  position: Offset,
  bounds: IntSize
): Boolean {
  return position.x >= 0 && position.x <= bounds.width &&
    position.y >= 0 && position.y <= bounds.height
}

private data class ThrottleClickableElement(
  val throttleTime: Long,
  val onClick: () -> Unit,
  val interactionSource: MutableInteractionSource?,
) : ModifierNodeElement<ThrottleClickableNode>() {
  override fun create(): ThrottleClickableNode {
    return ThrottleClickableNode(throttleTime, onClick, interactionSource)
  }

  override fun update(node: ThrottleClickableNode) {
    node.throttleTime = throttleTime
    node.onClick = onClick
    node.interactionSource = interactionSource
  }
}

@Composable
fun throttleFirst(
  throttleTimeMs: Long = 500L,
  block: () -> Unit,
): () -> Unit {
  val coroutineScope = rememberCoroutineScope()
  var invokable by remember { mutableStateOf(true) }

  return {
    if (invokable) {
      invokable = false
      coroutineScope.launch {
        delay(throttleTimeMs)
        invokable = true
      }
      block.invoke()
    }
  }
}

fun Modifier.throttleClickable(
  throttleTimeMs: Long = 500L,
  onClick: () -> Unit,
  interactionSource: MutableInteractionSource? = null,
  indication: Indication? = null
): Modifier {
  return this
    .then(
      if (interactionSource != null && indication != null) {
        Modifier
          .indication(
            interactionSource = interactionSource,
            indication = indication
          )
      } else {
        Modifier
      }
    )
    .then(ThrottleClickableElement(throttleTimeMs, onClick, interactionSource))
}



