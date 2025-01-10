package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.compose.component.SampleRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Sample(
  name = "Drag and drop sample",
  description = "Drag and drop",
  tags = ["Drag and drop"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/DragAndDropScreen.kt"
)
@Composable
fun DragAndDropScreen() {
  val stateHolder = rememberDragAndDropState()
  val uiState by stateHolder.state.collectAsStateWithLifecycle()

  DragAndDropContent(
    items = uiState.items,
    onItemMove = { from, to ->
      stateHolder.moveItem(from, to)
    }
  )
}

@Composable
private fun DragAndDropContent(
  items: List<SampleItem>,
  onItemMove: (from: Int, to: Int) -> Unit
) {
  val lazyListState = rememberLazyListState()

  var draggingItem: LazyListItemInfo? by remember { mutableStateOf(null) }

  var draggingYDelta: Float by remember {
    mutableFloatStateOf(0F)
  }

  LazyColumn(
    state = lazyListState,
    modifier = Modifier
      .padding(
        horizontal = 16.dp,
      )
      .pointerInput(
        key1 = "drag",
      ) {
        detectDragGesturesAfterLongPress(
          onDragStart = { offset ->
            lazyListState.layoutInfo.visibleItemsInfo
              .firstOrNull { item ->
                offset.y.toInt() in item.offset..(item.offset + item.size)
              }
              ?.let { draggedItem ->
                draggingItem = draggedItem
              }
          },
          onDrag = { change, dragAmount ->
            change.consume()

            draggingYDelta += dragAmount.y

            val currentDraggingItem =
              draggingItem ?: return@detectDragGesturesAfterLongPress

            val currentDraggingItemIndex = currentDraggingItem.index

            val startOffset = currentDraggingItem.offset + draggingYDelta
            // ドラッグしているアイテムの下端の位置
            val endOffset =
              currentDraggingItem.offset + currentDraggingItem.size + draggingYDelta
            val middleOffset = startOffset + (endOffset - startOffset) / 2

            val targetItem =
              lazyListState.layoutInfo.visibleItemsInfo.find { item ->
                middleOffset.toInt() in item.offset..item.offset + item.size &&
                  currentDraggingItem.index != item.index
              }
            if (targetItem != null) {
              val targetIndex = targetItem.index
              onItemMove.invoke(
                currentDraggingItemIndex,
                targetIndex
              )
              draggingItem = targetItem
              draggingYDelta += currentDraggingItem.offset - targetItem.offset
            }
          },
          onDragCancel = {
            draggingItem = null
          },
          onDragEnd = {
            draggingItem = null
          }
        )
      }
  ) {
    itemsIndexed(items) { index, item ->
      SampleRow(
        id = item.id,
        imageRes = item.imageRes,
        title = item.name,
        description = item.description,
        modifier = Modifier
          .fillMaxWidth()
          .scale(
            if (draggingItem?.index == index) 1.05F else 1F
          )
          .offset {
            IntOffset(
              y = if (draggingItem?.index == index) {
                draggingYDelta.toInt()
              } else 0,
              x = 0
            )
          }
      )
    }
  }
}

@Composable
fun rememberDragAndDropState(): DragAndDropState {
  return remember {
    DragAndDropState()
  }
}

class DragAndDropState {
  private val _state: MutableStateFlow<DragAndDropUiState> = MutableStateFlow(
    DragAndDropUiState()
  )

  val state: StateFlow<DragAndDropUiState> = _state.asStateFlow()

  fun moveItem(fromIndex: Int, toIndex: Int) {
    _state.update {
      val items = it.items.toMutableList()
      val item = items.removeAt(fromIndex)
      items.add(toIndex, item)
      it.copy(
        items = items
      )
    }
  }

  data class DragAndDropUiState(
    val items: List<SampleItem> = SampleItem.items
  )
}
