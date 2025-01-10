package com.mataku.snippets.ui.ext

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Modifier.noRippleClickable(
  onClick: () -> Unit,
): Modifier {
  val interactionSource = remember { MutableInteractionSource() }

  return this then clickable(
    indication = null,
    interactionSource = interactionSource,
    onClick = onClick
  )
}

class ThrottleClickableNode(
  var throttleTimeMs: Long,
  var onClick: () -> Unit,
  var interactionSource: MutableInteractionSource?,
) : PointerInputModifierNode, Modifier.Node() {
  private var invokable = true
  private var lastPress: PressInteraction.Press? = null

  override fun onPointerEvent(pointerEvent: PointerEvent, pass: PointerEventPass, bounds: IntSize) {
    if (invokable) {
      when (pointerEvent.type) {
        PointerEventType.Press -> {
          val lastChange = pointerEvent.changes.lastOrNull() ?: return
          /*
            Check position to avoid multiple Press events.
            1. Tap the target component and keep pressing
            2. Tap an area outside the component range
            3. Ripple effect is displayed unintentionally
           */
          if (pass == PointerEventPass.Main && positionWithinBounds(lastChange.position, bounds)) {
            val press = PressInteraction.Press(pointerEvent.changes.last().position)
            interactionSource?.tryEmit(press)
            lastPress = press
          }
        }

        PointerEventType.Release -> {
          if (pass == PointerEventPass.Main) {
            val lastChange = pointerEvent.changes.lastOrNull() ?: return
            if (invokable && lastPress != null) {
              invokable = false
              coroutineScope.launch {
                delay(throttleTimeMs)
                invokable = true
              }
              // Check if the Release is within the tapped area
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
          if (pass == PointerEventPass.Main && lastPress != null) {
            val lastChange = pointerEvent.changes.lastOrNull() ?: return
            if (!positionWithinBounds(lastChange.position, bounds)) {
              interactionSource?.tryEmit(
                PressInteraction.Cancel(lastPress!!)
              )
              lastPress = null
            }
          }
        }
      }
    }
  }

  override fun onCancelPointerInput() {
    if (lastPress != null) {
      interactionSource?.tryEmit(
        PressInteraction.Cancel(lastPress!!)
      )
    }
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
  val throttleTimeMs: Long,
  val onClick: () -> Unit,
  val interactionSource: MutableInteractionSource?,
) : ModifierNodeElement<ThrottleClickableNode>() {
  override fun create(): ThrottleClickableNode {
    return ThrottleClickableNode(throttleTimeMs, onClick, interactionSource)
  }

  override fun update(node: ThrottleClickableNode) {
    node.throttleTimeMs = throttleTimeMs
    node.onClick = onClick
    node.interactionSource = interactionSource
  }
}

// for Button, IconButton, etc.
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

/*
  example:

  Text(
    text = "Click!",
    modifier = Modifier
      .throttleClickable(
        throttleTimeMs = 500L,
        onClick = {
          // action
        },
        // If you want to apply ripple, specify interactionSource and indication
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple()
      )
      .padding(
        horizontal = 16.dp,
        vertical = 8.dp,
      )
      ...
  )
*/
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
