@file:OptIn(ExperimentalFoundationApi::class)

package com.mataku.snippets.ui.compose.sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.compose.component.TagChip
import com.mataku.snippets.ui.compose.component.Title
import com.mataku.snippets.ui.viewmodel.LazyRowViewModel
import com.mataku.snippets.ui.viewmodel.TagState
import com.mataku.snippets.ui.viewmodel.TagState3
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Sample(
  name = "LazyRow",
  description = "LazyRow sample",
  tags = ["LazyRow", "Animation"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/LazyRowScreenScreen.kt"
)
@Composable
fun LazyRowSampleScreen() {
  val viewModel: LazyRowViewModel = viewModel()
  val uiState by viewModel.uiState.collectAsState()

  Column {
    ItemPlacementSample(
      tagList = uiState.tagListItemPlacement,
      onTagClicked = viewModel::onTagClickItemPlacement,
      modifier = Modifier
    )
    Spacer(modifier = Modifier.height(24.dp))

    ItemFadeInOutSample(
      tagList = uiState.tagList,
      onTagClicked = viewModel::onTagClick,
      modifier = Modifier,
      shouldShowTagList = uiState.shouldShowTagList,
      onClear = viewModel::clearAllSelection,
      canClearAllSelection = uiState.canClearAllSelection
    )

    ItemSlideInAndFadeInSample(
      tagList = uiState.tagList3,
      onTagClicked = viewModel::onTagSelected3,
      modifier = Modifier
    )
  }
}

@Composable
private fun ClearSelectionButton(
  onClear: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(CircleShape)
      .background(color = MaterialTheme.colors.onSurface)
      .clickable {
        onClear.invoke()
      }
  ) {
    Icon(
      painter = rememberVectorPainter(
        image = Icons.Filled.Clear
      ),
      contentDescription = "Clear all selection",
      tint = MaterialTheme.colors.surface,
      modifier = Modifier
        .padding(8.dp)
    )
  }
}

@Composable
private fun ItemFadeInOutSample(
  tagList: ImmutableList<TagState>,
  shouldShowTagList: Boolean,
  canClearAllSelection: Boolean,
  onTagClicked: (TagState) -> Unit,
  onClear: () -> Unit,
  modifier: Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val tagListState = rememberLazyListState()
  Column(modifier = modifier) {
    Title(
      label = "Animate show/hide effects and slideIn",
      modifier = Modifier
        .padding(16.dp)
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .height(40.dp)
    ) {
      if (canClearAllSelection) {
        Spacer(modifier = Modifier.width(16.dp))
        ClearSelectionButton(
          onClear = onClear
        )
      } else {
        Spacer(modifier = Modifier.width(8.dp))
      }


      AnimatedVisibility(
        visible = shouldShowTagList,
        enter = fadeIn(animationSpec = tween(200)) + slideInHorizontally(
          initialOffsetX = { fullWidth -> fullWidth }
        ),
        exit = fadeOut(animationSpec = tween(200))
      ) {
        LazyRow(
          content = {
            items(
              tagList,
              key = { it.label.hashCode() }
            ) { tag ->
              TagChip(
                label = tag.label,
                selected = tag.selected,
                modifier = Modifier,
                onClick = {
                  onTagClicked.invoke(tag)
                  coroutineScope.launch {
                    tagListState.scrollToItem(0)
                  }
                },
              )
            }
          },
          modifier = modifier,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
        )
      }
    }
  }
}

@Composable
private fun ItemPlacementSample(
  tagList: ImmutableList<TagState>,
  onTagClicked: (TagState) -> Unit,
  modifier: Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val tagListState = rememberLazyListState()
  Column(modifier = modifier) {
    Title(
      label = "Animate item placement",
      modifier = Modifier
        .padding(16.dp)
    )

    LazyRow(
      content = {
        items(
          tagList,
          key = { tag -> tag.label.hashCode() },
        ) { tag ->
          TagChip(
            label = tag.label,
            selected = tag.selected,
            modifier = Modifier
              .animateItemPlacement(),
            onClick = {
              onTagClicked.invoke(tag)
              coroutineScope.launch {
                tagListState.scrollToItem(0)
              }
            },
          )
        }
      },
      modifier = modifier,
      state = tagListState,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp)
    )
  }
}

@Composable
private fun ItemSlideInAndFadeInSample(
  tagList: ImmutableList<TagState3>,
  onTagClicked: (TagState3) -> Unit,
  modifier: Modifier
) {
  val tagListState = rememberLazyListState()
  Column(modifier = modifier) {
    Title(
      label = "Animate slideIn and fadeIn",
      modifier = Modifier
        .padding(16.dp)
    )
    Row {

      LazyRow(
        content = {
          items(tagList, key = { tag -> tag.label.hashCode() }) { tag ->
            TagChip(
              label = tag.label,
              selected = tag.selected,
              modifier = Modifier
                .animateItemPlacement()
                .alpha(
                  if (tag.enabled) {
                    1F
                  } else {
                    0F
                  }
                ),
              onClick = {
                onTagClicked.invoke(tag)
              }
            )
          }
        },
        modifier = modifier,
        state = tagListState
      )
    }
  }
}
