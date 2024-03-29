package com.mataku.snippets.ui.compose.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mataku.snippets.R
import com.mataku.snippets.entity.Banner
import com.mataku.snippets.ui.compose.preview.MultiThemePreview

// Looping HorizontalPager
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoopingHorizontalBannerPagerSample(
  modifier: Modifier = Modifier,
  bannerList: List<Banner>,
) {
  val scrollableList = if (bannerList.size > 1) {
    listOf(bannerList.last()) + bannerList + listOf(bannerList.first())
  } else {
    bannerList
  }
  val pagerState = rememberPagerState(
    initialPage = scrollableList.size / 2,
    pageCount = {
      scrollableList.size
    }
  )
  Column(modifier = Modifier.fillMaxSize()) {
    HorizontalPager(
      state = pagerState,
      modifier = modifier,
      pageContent = { index ->
        val banner = scrollableList[index]
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Image(
            painter = painterResource(id = banner.resId),
            contentDescription = "banner image",
            modifier = Modifier
              .fillMaxWidth(),
            contentScale = ContentScale.Crop,
          )
          Text(
            text = banner.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .padding(16.dp),
            color = MaterialTheme.colors.onPrimary
          )
        }
      }
    )
    if (pagerState.currentPage == 0 && !pagerState.isScrollInProgress) {
      LaunchedEffect(Unit) {
        pagerState.scrollToPage(scrollableList.lastIndex - 1)
      }
    }

    if (pagerState.currentPage == scrollableList.lastIndex && !pagerState.isScrollInProgress) {
      LaunchedEffect(Unit) {
        pagerState.scrollToPage(1)
      }
    }

    val currentPosition = pagerState.currentPage

    Spacer(modifier = Modifier.height(24.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "Current position",
        textAlign = TextAlign.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        color = MaterialTheme.colors.onPrimary
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        IntRange(0, scrollableList.lastIndex).forEach {
          SelectionStatus(
            selected = currentPosition == it,
            modifier = Modifier
              .padding(16.dp)
          )
        }
      }
    }
  }
}

@Composable
private fun SelectionStatus(
  modifier: Modifier = Modifier,
  selected: Boolean
) {
  val circleColor = if (selected) {
    Color.Black
  } else {
    Color.LightGray
  }

  Box(
    modifier = modifier
      .size(32.dp)
      .clip(CircleShape)
      .background(color = circleColor),
    contentAlignment = Alignment.Center
  ) {
  }
}

@Composable
@MultiThemePreview
private fun LoopingHorizontalBannerPagerPreview() {
  MaterialTheme {
    Surface {
      LoopingHorizontalBannerPagerSample(
        bannerList = listOf(
          Banner(
            resId = R.drawable.ic_launcher_background,
            title = "title A"
          ),
          Banner(
            resId = R.drawable.ic_launcher_background,
            title = "title A"
          ),
          Banner(
            resId = R.drawable.ic_launcher_background,
            title = "title A"
          )
        )
      )
    }
  }
}

@Composable
@MultiThemePreview
private fun TextInCirclePreview() {
  MaterialTheme {
    Surface {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
      ) {
        SelectionStatus(
          selected = false,
          modifier = Modifier
            .padding(16.dp)
        )
        SelectionStatus(
          selected = true,
          modifier = Modifier
            .padding(16.dp)
        )
        SelectionStatus(
          selected = false,
          modifier = Modifier
            .padding(16.dp)
        )
      }
    }
  }
}


