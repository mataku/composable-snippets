@file:OptIn(ExperimentalPagerApi::class)

package com.mataku.snippets.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mataku.snippets.R
import com.mataku.snippets.entity.Banner
import com.mataku.snippets.ui.compose.preview.MultiThemePreview

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
  Column(modifier = Modifier.fillMaxSize()) {
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
          TextInCircle(
            text = it.toString(),
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
private fun TextInCircle(
  text: String,
  modifier: Modifier = Modifier,
  selected: Boolean
) {
  val borderColor = if (selected) {
    MaterialTheme.colors.onPrimary
  } else {
    Color.LightGray
  }
  val borderWidth = if (selected) {
    2.dp
  } else {
    1.dp
  }
  Box(
    modifier = modifier
      .size(32.dp)
      .clip(CircleShape)
      .border(
        width = borderWidth,
        color = borderColor,
        shape = CircleShape
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      fontSize = 16.sp,
      color = Color.Gray,
      fontWeight = FontWeight.Bold
    )
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
        TextInCircle(
          text = "1",
          selected = false,
          modifier = Modifier
            .padding(16.dp)
        )
        TextInCircle(
          text = "2",
          selected = true,
          modifier = Modifier
            .padding(16.dp)
        )
        TextInCircle(
          text = "3",
          selected = false,
          modifier = Modifier
            .padding(16.dp)
        )
      }
    }
  }
}


