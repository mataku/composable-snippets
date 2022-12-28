@file:OptIn(
  ExperimentalFoundationApi::class,
  ExperimentalComposeUiApi::class
)

package com.mataku.jetpackcomposesandbox.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.mataku.jetpackcomposesandbox.R
import com.mataku.jetpackcomposesandbox.entity.Banner
import com.mataku.jetpackcomposesandbox.ui.compose.component.LoopingHorizontalBannerPagerSample

@Composable
fun MainScreen() {
  val lazyListState = rememberLazyListState()

  LazyColumn(
    state = lazyListState,
    content = {
      item {
        LoopingHorizontalBannerPagerSample(
          bannerList = listOf(
            Banner(
              resId = R.drawable.kota,
              title = "title 1"
            ),
            Banner(
              resId = R.drawable.kota,
              title = "title 2"
            ),
            Banner(
              resId = R.drawable.kota,
              title = "title 3"
            ),
          )
        )
      }
    },
    modifier = Modifier.fillMaxSize()
  )
}
