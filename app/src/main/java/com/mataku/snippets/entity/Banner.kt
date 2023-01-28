package com.mataku.snippets.entity

import androidx.annotation.DrawableRes

data class Banner(
  @DrawableRes val resId: Int,
  val title: String
)
