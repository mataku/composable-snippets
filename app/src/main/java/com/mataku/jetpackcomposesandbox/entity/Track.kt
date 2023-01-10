package com.mataku.jetpackcomposesandbox.entity

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.mataku.jetpackcomposesandbox.R

data class Track(
  val name: String,
  val artistName: String,
  val imageResId: Int
) {
  companion object {
    fun generateList(): List<Track> {
      return List(100) {
        Track(
          name = LoremIpsum(4).values.joinToString(),
          artistName = LoremIpsum(8).values.joinToString(),
          imageResId = R.drawable.kota
        )
      }
    }
  }
}
