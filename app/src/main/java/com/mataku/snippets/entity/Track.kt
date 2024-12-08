package com.mataku.snippets.entity

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.mataku.snippets.R

data class Track(
  val name: String,
  val artistName: String,
  val imageResId: Int
) {
  companion object {
    fun generateList(): List<Track> {
      return List(10) { index ->
        Track(
          name = "$index: ${LoremIpsum(4).values.joinToString()}",
          artistName = LoremIpsum(8).values.joinToString(),
          imageResId = R.drawable.kota
        )
      }
    }

    fun generateList2(): List<Track> {
      return List(10) { index ->
        Track(
          name = "second $index: ${LoremIpsum(4).values.joinToString()}",
          artistName = LoremIpsum(8).values.joinToString(),
          imageResId = R.drawable.kota
        )
      }
    }
  }
}
