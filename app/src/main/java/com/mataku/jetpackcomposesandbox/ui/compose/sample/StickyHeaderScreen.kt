@file:OptIn(ExperimentalFoundationApi::class)

package com.mataku.jetpackcomposesandbox.ui.compose.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.jetpackcomposesandbox.entity.Track
import com.mataku.jetpackcomposesandbox.ui.Colors

@Sample(
  name = "Sticky header sample",
  description = "Sticky header with LazyList",
  tags = ["Sticky header", "LazyColumn"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/StickyHeaderScreen.kt"
)
@Composable
fun StickyHeaderScreen() {
  var selectedTabIndex by remember {
    mutableStateOf(0)
  }

  val itemList = Track.generateList()

  LazyColumn(
    content = {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(
              color = Colors.ContentBackground
            ),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Banner Sample",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary
          )
        }
      }
      stickyHeader {
        TabRow(
          selectedTabIndex = selectedTabIndex,
          modifier = Modifier
            .height(48.dp),
          backgroundColor = if (isSystemInDarkTheme()) {
            Color.Black
          } else {
            Color.White
          },
          contentColor = MaterialTheme.colors.onPrimary,
        ) {
          repeat(5) {
            Tab(
              selected = selectedTabIndex == it,
              onClick = {
                selectedTabIndex = it
              },
            ) {
              Text(
                text = it.toString(),
                color = MaterialTheme.colors.onPrimary
              )
            }
          }
        }
      }
      items(itemList) {
        TrackContent(track = it)
        Divider()
      }

    },
    modifier = Modifier
      .fillMaxSize(),
  )
}

@Composable
private fun TrackContent(
  track: Track,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 16.dp)
  ) {
    Image(
      painter = painterResource(id = track.imageResId),
      contentDescription = "Track artwork",
      modifier = Modifier.size(64.dp)
    )

    Column(
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = track.name,
        modifier = Modifier
          .padding(start = 16.dp),
        color = MaterialTheme.colors.onPrimary,
        fontSize = 16.sp
      )

      Text(
        text = track.artistName,
        modifier = Modifier
          .padding(start = 16.dp),
        color = Colors.textSecondary,
        fontSize = 14.sp
      )
    }
  }
}
