package com.mataku.jetpackcomposesandbox.ui.compose.sample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.jetpackcomposesandbox.R
import com.mataku.jetpackcomposesandbox.entity.Banner
import com.mataku.jetpackcomposesandbox.ui.compose.component.LoopingHorizontalBannerPagerSample

@Sample(
  name = "Looping HorizontalPager",
  description = "Infinite HorizontalPager looping",
  tags = ["HorizontalPager"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/LoopingHorizontalPagerScreen.kt"
)
@Composable
fun LoopingHorizontalPagerScreen() {
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
