@file:OptIn(ExperimentalFoundationApi::class)

package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.entity.Track
import com.mataku.snippets.ui.Colors

@Sample(
  name = "Sticky header sample",
  description = "Sticky header with LazyList",
  tags = ["Sticky header", "LazyColumn"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/StickyHeaderScreen.kt"
)
@Composable
fun StickyHeaderScreen() {
  val itemList = Track.generateList()
  val itemList2 = Track.generateList2()

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
            color = MaterialTheme.colorScheme.onPrimary
          )
        }
      }
      stickyHeader(
        key = "stickyHeader_1",
      ) {
        Text(
          text = "stickyHeader 1",
          modifier = Modifier
            .fillMaxWidth()
            .background(
              color = Color.LightGray
            )
            .padding(
              16.dp
            ),
          fontSize = 18.sp,
          fontWeight = FontWeight.Medium
        )
      }
      itemsIndexed(
        itemList, key = { index, _ ->
          "track1_$index"
        }
      ) { _, item ->
        TrackContent(track = item)
        Divider()
      }
      stickyHeader(
        key = "stickyHeader_2",
      ) {
        Text(
          text = "stickyHeader 2",
          modifier = Modifier
            .fillMaxWidth()
            .background(
              color = Color.LightGray
            )
            .padding(
              16.dp
            ),
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )
      }
      itemsIndexed(
        itemList2, key = { index, _ ->
          "track2_$index"
        }
      ) { _, item ->
        TrackContent(track = item)
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
      .padding(horizontal = 16.dp, vertical = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
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
        color = MaterialTheme.colorScheme.onSurface,
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
