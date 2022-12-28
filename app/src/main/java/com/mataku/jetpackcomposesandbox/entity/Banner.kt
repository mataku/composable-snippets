package com.mataku.jetpackcomposesandbox.entity

import androidx.annotation.DrawableRes

data class Banner(
  @DrawableRes val resId: Int,
  val title: String
)
