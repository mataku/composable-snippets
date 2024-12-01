package com.mataku.snippets.ui.compose.sample

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample
import kotlinx.coroutines.launch

@Sample(
  name = "Scale animation sample",
  description = "Size scaling",
  tags = ["Scale animation"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/DragAndDropScreen.kt"
)
@Composable
fun ScaleAnimationScreen() {
  val itemListState = rememberLazyListState()
  val pagerState = rememberPagerState {
    3
  }
  val coroutineScope = rememberCoroutineScope()

  Column(modifier = Modifier.fillMaxSize()) {
    ScaleAnimationHeader2(
      scrolled = itemListState.canScrollBackward,
      modifier = Modifier,
      onTapHeader = {
        coroutineScope.launch {
          pagerState.scrollToPage(it)
        }
      },
      selectedIndex = pagerState.currentPage
    )

    HorizontalPager(
      state = pagerState,
      userScrollEnabled = false
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize()
      ) {
        items(100) {
          Text(
            text = "Number $it",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
              .fillMaxWidth()
              .padding(
                16.dp
              )
              .background(
                if (it % 2 == 0) {
                  Color.Gray
                } else {
                  Color.White
                }
              )
          )
        }
      }
    }
  }
}

@Composable
private fun ScaleAnimationHeader(
  scrolled: Boolean,
  modifier: Modifier = Modifier,
  onTapHeader: (Int) -> Unit
) {
  println("MATAKUDEBUG scrolled $scrolled")
  val scaleValue by animateFloatAsState(
    targetValue = if (scrolled) 0.8F else 1.0F,
    label = "header_scale",
    animationSpec = tween(200)
  )

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(44.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
  ) {
    ScaleAnimationHeaderTabList(
      modifier = Modifier
        .weight(1F)
        .graphicsLayer {
          scaleX = scaleValue
          scaleY = scaleValue
        }
        .animateContentSize(),
      onTapHeader = onTapHeader
    )

    Image(
      painter = rememberVectorPainter(Icons.Outlined.AccountBox),
      contentDescription = null
    )
  }
}

@Composable
private fun ScaleAnimationHeaderTabList2(
  modifier: Modifier = Modifier,
  onTapHeader: (Int) -> Unit,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
  ) {
    ScaleAnimationHeaderTab("Tab 1", true, Modifier.clickable {
      onTapHeader.invoke(0)
    })
    Spacer(modifier = Modifier.width(10.dp))
    ScaleAnimationHeaderTab("Tab 2", false, Modifier.clickable {
      onTapHeader.invoke(1)
    })
    Spacer(modifier = Modifier.width(10.dp))
    ScaleAnimationHeaderTab("Tab 3", false, Modifier.clickable {
      onTapHeader.invoke(2)
    })
  }
}

@Composable
private fun ScaleAnimationHeaderTabList(
  modifier: Modifier = Modifier,
  onTapHeader: (Int) -> Unit
) {
  ScrollableTabRow(
    selectedTabIndex = 0,
    modifier = Modifier,
    backgroundColor = Color.Transparent,
    contentColor = Color.Transparent,
    indicator = {},
    divider = {},
  ) {
    ScaleAnimationHeaderTab(
      "Tab 1",
      true,
      modifier
        .clickable {
          onTapHeader.invoke(0)
        }
    )
    ScaleAnimationHeaderTab(
      "Tab 2",
      false,
      modifier
        .clickable {
          onTapHeader.invoke(1)
        }
    )
    ScaleAnimationHeaderTab("Tab 3", false, modifier
      .clickable {
        onTapHeader.invoke(2)
      }
    )
  }
}

@Composable
private fun ScaleAnimationHeaderTab(
  name: String,
  selected: Boolean,
  modifier: Modifier = Modifier,
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .height(44.dp)
      .then(
        if (selected) {
          Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colors.onSurface)
        } else {
          Modifier
        }
      )
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.body1,
      color = Color.Gray,
      modifier = Modifier
        .padding(
          horizontal = 10.dp
        ),
      fontSize = 14.sp,
    )
  }
}

@Composable
private fun ScaleAnimationHeader2(
  scrolled: Boolean,
  selectedIndex: Int,
  onTapHeader: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  println("MATAKUDEBUG scrolled $scrolled")
  val scaleValue by animateFloatAsState(
    targetValue = if (scrolled) 0.8F else 1.0F,
    label = "header_scale",
    animationSpec = tween(200)
  )

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(44.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
  ) {
    ScaleAnimationHeaderTabList2(
      modifier = Modifier
        .weight(1F)
        .graphicsLayer {
          scaleX = scaleValue
          scaleY = scaleValue
        },
      onTapHeader = onTapHeader
    )

    Image(
      painter = rememberVectorPainter(Icons.Outlined.AccountBox),
      contentDescription = null
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun ScaleAnimationHeaderTabPreview() {
  MaterialTheme {
    Surface {
      ScaleAnimationHeader(
        scrolled = true,
        onTapHeader = {}
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ScaleAnimationHeaderTab2Preview() {
  MaterialTheme {
    Surface {
      ScaleAnimationHeader2(
        scrolled = false,
        onTapHeader = {},
        selectedIndex = 0
      )
    }
  }
}
