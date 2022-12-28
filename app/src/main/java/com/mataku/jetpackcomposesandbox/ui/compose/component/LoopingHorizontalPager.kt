@file:OptIn(ExperimentalPagerApi::class)

package com.mataku.jetpackcomposesandbox.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mataku.jetpackcomposesandbox.R
import com.mataku.jetpackcomposesandbox.entity.Banner
import com.mataku.jetpackcomposesandbox.ui.preview.MultiThemePreview

// Looping HorizontalPager
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

  val pagerState = rememberPagerState(initialPage = scrollableList.size / 2)
  HorizontalPager(
    state = pagerState,
    count = scrollableList.size,
    modifier = modifier,
    content = { index ->
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
          modifier = Modifier
            .padding(16.dp),
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
          )
        )
      )
    }
  }
}


