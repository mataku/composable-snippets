package com.mataku.snippets.ui.compose.sample

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.ext.noRippleClickable


@Sample(
  name = "Moving Tab Animation",
  description = "Moving Tab Animation",
  tags = ["Animation", "Tab"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/TabMovingScreen.kt"
)
@Composable
fun TabMovingScreen() {
  var selectedIndex by remember {
    mutableIntStateOf(1)
  }
  ComponentMovingContent(
    selectedIndex = selectedIndex,
    onClickTab = { index ->
      selectedIndex = index
    }
  )
}

@Composable
private fun ComponentMovingContent(
  selectedIndex: Int,
  onClickTab: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        16.dp
      )
  ) {
    val lazyListState = rememberLazyListState()
    Header(
      selectedIndex = selectedIndex,
      onClickTab = onClickTab,
      isScrolled = lazyListState.canScrollBackward,
    )

    LazyColumn(
      modifier = Modifier.fillMaxWidth(),
      state = lazyListState,
    ) {
      items(20) {
        Cell(text = "Item $it")
        Divider()
      }
    }
  }
}

@Composable
private fun Header(
  selectedIndex: Int,
  isScrolled: Boolean,
  onClickTab: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  var currentIndex = 1
  val radius = with(LocalDensity.current) {
    CornerRadius(16.dp.toPx())
  }
  val tabHorizontalOffsetSpace by animateDpAsState(
    targetValue = if (isScrolled) {
      12.dp // (tab width difference * 2) + (space difference)
    } else {
      0.dp
    },
    label = "tabHorizontalOffsetSpace"
  )
  val tabHorizontalSpace by animateDpAsState(
    targetValue = if (isScrolled) {
      4.dp
    } else {
      8.dp
    },
    label = "tabHorizontalSpace"
  )
  val offsetXAnimatable = remember {
    Animatable(0F)
  }
  val density = LocalDensity.current
  val tabWidth by animateDpAsState(
    targetValue = if (isScrolled) {
      48.dp
    } else {
      52.dp
    },
    label = "tabWidth_"
  )
  val tabHeight by animateDpAsState(
    targetValue = if (isScrolled) {
      24.dp
    } else {
      28.dp
    },
    label = "tabHeight"
  )

  LaunchedEffect(selectedIndex) {
    offsetXAnimatable.animateTo(
      targetValue = with(density) {
        (tabWidth.toPx() + tabHorizontalSpace.toPx()) * (selectedIndex - currentIndex)
      },
      animationSpec = tween(200)
    )
    currentIndex = selectedIndex
  }

  LaunchedEffect(isScrolled) {
    offsetXAnimatable.animateTo(
      targetValue = with(density) {
        (tabWidth.toPx() + tabHorizontalOffsetSpace.toPx()) * (selectedIndex - currentIndex)
      },
      animationSpec = tween(100)
    )
    currentIndex = selectedIndex
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(44.dp),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .offset {
          IntOffset(
            offsetXAnimatable.value.toInt(), 0
          )
        }
        .drawWithContent {
          drawRoundRect(
            color = Color.LightGray,
            cornerRadius = radius
          )
          drawContent()
        }
        .layout { measurable, _ ->
          val placeable = measurable.measure(
            Constraints.fixed(
              width = tabWidth
                .toPx()
                .toInt(),
              height = tabHeight
                .toPx()
                .toInt()
            )
          )
          layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
          }
        }
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      AnimationHeaderTab(
        name = "Tab 1",
        modifier = Modifier
          .noRippleClickable {
            onClickTab.invoke(0)
          }
          .layout { measurable, _ ->
            val placeable = measurable.measure(
              Constraints.fixed(
                width = tabWidth
                  .toPx()
                  .toInt(),
                height = tabHeight
                  .toPx()
                  .toInt()
              )
            )
            layout(placeable.width, placeable.height) {
              placeable.place(0, 0)
            }
          },
        isSelected = selectedIndex == 0,
        isScrolled = isScrolled
      )
      Spacer(
        modifier = Modifier.width(tabHorizontalSpace)
      )
      AnimationHeaderTab(
        name = "Tab 2",
        modifier = Modifier
          .noRippleClickable {
            onClickTab.invoke(1)
          }
          .layout { measurable, _ ->
            val placeable = measurable.measure(
              Constraints.fixed(
                width = tabWidth
                  .toPx()
                  .toInt(),
                height = tabHeight
                  .toPx()
                  .toInt()
              )
            )
            layout(placeable.width, placeable.height) {
              placeable.place(0, 0)
            }
          },
        isSelected = selectedIndex == 1,
        isScrolled = isScrolled
      )
      Spacer(
        modifier = Modifier.width(tabHorizontalSpace)
      )
      AnimationHeaderTab(
        name = "Tab 3",
        modifier = Modifier
          .noRippleClickable {
            onClickTab.invoke(2)
          }
          .layout { measurable, _ ->
            val placeable = measurable.measure(
              Constraints.fixed(
                width = tabWidth
                  .toPx()
                  .toInt(),
                height = tabHeight
                  .toPx()
                  .toInt()
              )
            )
            layout(placeable.width, placeable.height) {
              placeable.place(0, 0)
            }
          },
        isSelected = selectedIndex == 2,
        isScrolled = isScrolled
      )
    }
  }
}

@Composable
private fun AnimationHeaderTab(
  name: String,
  isSelected: Boolean,
  isScrolled: Boolean,
  modifier: Modifier = Modifier,
) {
  val contentSelected by remember {
    mutableStateOf(isSelected)
  }
  val tabTextFont by animateDpAsState(
    targetValue = if (isScrolled) {
      12.dp
    } else {
      14.dp
    },
    label = "tabTextFont",
    animationSpec = tween(200),
  )
  val tabTextColor by animateColorAsState(
    targetValue = if (contentSelected) {
      Color.White
    } else {
      Color.Gray
    },
    label = "tabTextColor",
    animationSpec = tween(200),
  )
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.body1,
      color = tabTextColor,
      modifier = Modifier,
      fontSize = with(LocalDensity.current) {
        tabTextFont.toSp()
      },
      letterSpacing = 0.sp,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
private fun Cell(
  text: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .padding(
        vertical = 12.dp
      )
  ) {
    Text(
      text = text
    )
  }
}


@Preview(showBackground = true)
@Composable
private fun ComponentMovingScreenPreview() {
  MaterialTheme {
    Surface {
      ComponentMovingContent(
        selectedIndex = 1,
        onClickTab = {}
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ComponentMovingScreenPreview_2() {
  MaterialTheme {
    Surface {
      ComponentMovingContent(
        selectedIndex = 2,
        onClickTab = {}
      )
    }
  }
}
