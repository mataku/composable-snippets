package com.mataku.jetpackcomposesandbox.ui.compose.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.catalog.framework.annotations.Sample

@Sample(
  name = "FixedFooter",
  description = "fixed footer"
)
@Composable
fun FixedFooter() {
  Column(modifier = Modifier.fillMaxSize()) {

  }
}

@Composable
private fun FooterContent2(
  description: String
) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp)
  ) {
    val (title, sub, subIcon, spacer, endIcon) = createRefs()
    Text(
      text = "title",
      fontSize = 16.sp,
      modifier = Modifier
        .constrainAs(title) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
          end.linkTo(sub.start)
          width = Dimension.wrapContent
        }
    )

//    Row(
//      modifier = Modifier
//        .constrainAs(sub) {
//          top.linkTo(parent.top)
//          bottom.linkTo(parent.bottom)
//          start.linkTo(title.end)
//          end.linkTo(spacer.start)
//          width = Dimension.fillToConstraints
//        },
//    ) {
//      Text(
//        text = description,
//        fontSize = 14.sp,
//        maxLines = 1,
//        overflow = TextOverflow.Ellipsis,
//        modifier = Modifier
//      )
//      Box(
//        modifier = Modifier
//          .size(14.dp)
//          .clip(CircleShape)
//          .background(color = Color.Black)
//          .weight(1f, fill = false)
//      )
//    }

    ConstraintLayout(
      modifier = Modifier
        .constrainAs(sub) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(title.end, margin = 6.dp)
          end.linkTo(spacer.start)
          width = Dimension.fillToConstraints
        }
        .padding(start = 6.dp),
    ) {
      val (text, iconImage) = createRefs()
      Text(
        text = description,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .constrainAs(text) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(iconImage.start)
            width = Dimension.preferredWrapContent
          }
          .background(color = Color.Gray),
        textAlign = TextAlign.Left
      )
      Box(
        modifier = Modifier
          .size(14.dp)
          .clip(CircleShape)
          .background(color = Color.Black)
          .constrainAs(iconImage) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(text.end)
            end.linkTo(parent.end)
            horizontalChainWeight = 1f
          }
      )
      createHorizontalChain(text, iconImage, chainStyle = ChainStyle.Packed)
    }


//    Text(
//      text = description,
//      fontSize = 14.sp,
//      maxLines = 1,
//      overflow = TextOverflow.Ellipsis,
//      modifier = Modifier
//        .constrainAs(sub) {
//          top.linkTo(parent.top)
//          bottom.linkTo(parent.bottom)
//          start.linkTo(title.end, margin = 6.dp)
//          end.linkTo(subIcon.start)
//          width = Dimension.fillToConstraints
//        }
//    )
//    Box(
//      modifier = Modifier
//        .size(14.dp)
//        .clip(CircleShape)
//        .background(color = Color.Black)
//        .constrainAs(subIcon) {
//          top.linkTo(parent.top)
//          bottom.linkTo(parent.bottom)
//          start.linkTo(sub.end)
//          end.linkTo(spacer.start)
//          width = Dimension.wrapContent
//        }
//    ) {}

    Spacer(modifier = Modifier
      .constrainAs(spacer) {
        start.linkTo(sub.end)
        end.linkTo(endIcon.start)
        horizontalChainWeight = 1f
      }
      .defaultMinSize(16.dp)
    )

    Box(
      modifier = Modifier
        .size(14.dp)
        .clip(CircleShape)
        .background(color = Color.Cyan)
        .constrainAs(endIcon) {
          end.linkTo(parent.end)
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(spacer.end)
          width = Dimension.wrapContent
          horizontalChainWeight = 1f
        }
    ) {}

    createHorizontalChain(title, sub, spacer, endIcon, chainStyle = ChainStyle.Packed)
  }
}

@Composable
private fun FooterContent3(
  description: String
) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp)
  ) {
    val (title, subText, newIcon, spacer, endIcon) = createRefs()
    Text(
      text = "title",
      fontSize = 16.sp,
      modifier = Modifier
        .constrainAs(title) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
          end.linkTo(subText.start)
          width = Dimension.wrapContent
        }
    )
    Text(
      text = description,
      fontSize = 14.sp,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .constrainAs(subText) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(title.end)
          end.linkTo(newIcon.start)
          width = Dimension.fillToConstraints
        }
        .defaultMinSize(16.dp)
        .background(color = Color.Gray),
      softWrap = false
    )

    Box(
      modifier = Modifier
        .size(14.dp)
        .clip(CircleShape)
        .background(color = Color.Black)
        .constrainAs(newIcon) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(subText.end)
          end.linkTo(spacer.start)
          width = Dimension.wrapContent
        }
        .defaultMinSize(14.dp)
    )
    Spacer(modifier = Modifier
      .constrainAs(spacer) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(newIcon.end)
        end.linkTo(endIcon.start)
        width = Dimension.preferredWrapContent
        horizontalChainWeight = 1f
      }
      .defaultMinSize(16.dp)
    )

    Box(
      modifier = Modifier
        .size(14.dp)
        .clip(CircleShape)
        .background(color = Color.Cyan)
        .constrainAs(endIcon) {
          end.linkTo(parent.end)
          top.linkTo(parent.top)
          start.linkTo(spacer.end)
          bottom.linkTo(parent.bottom)
          horizontalChainWeight = 1f
          width = Dimension.wrapContent
        }
    ) {}
    createHorizontalChain(title, subText, newIcon, spacer, endIcon, chainStyle = ChainStyle.Packed)
  }
}

@Composable
private fun FooterContent(
  description: String
) {
//  Row(
//    modifier = Modifier
//      .fillMaxWidth()
//      .height(40.dp)
//  ) {
//    Row(
//      horizontalArrangement = Arrangement.End,
//      verticalAlignment = Alignment.CenterVertically,
//      modifier = Modifier
//        .fillMaxWidth()
//        .background(color = Color.Transparent)
//    ) {
//      Box(
//        modifier = Modifier
//          .size(14.dp)
//          .clip(CircleShape)
//          .background(color = Color.Cyan)
//      ) {}
//    }
//    Row(
//      modifier = Modifier
//        .wrapContentWidth()
//        .height(40.dp),
//      verticalAlignment = Alignment.CenterVertically,
//      horizontalArrangement = Arrangement.Start
//    ) {
//      Text(
//        text = "title",
//        fontSize = 16.sp
//      )
//      Spacer(modifier = Modifier.width(6.dp))
//      Text(
//        text = description,
//        maxLines = 1,
//        overflow = TextOverflow.Ellipsis,
//        fontSize = 14.sp,
//      )
//      Box(
//        modifier = Modifier
//          .size(14.dp)
//          .background(color = Color.Black)
//          .clip(CircleShape)
//          .weight(1f, fill = false)
//      ) {}
//      Spacer(modifier = Modifier.weight(1f))
//    }
//  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp),
    horizontalArrangement = Arrangement.End
  ) {
    Text(
      text = "title",
      fontSize = 16.sp,
      maxLines = 1,
      modifier = Modifier
    )
    Text(
      text = description,
      fontSize = 14.sp,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
    )
    Box(
      modifier = Modifier
        .size(14.dp)
        .background(color = Color.Black)
        .clip(CircleShape)
    ) {}

    Spacer(modifier = Modifier.weight(1f))

    Box(
      modifier = Modifier
        .size(14.dp)
        .background(color = Color.Cyan)
        .clip(CircleShape)
        .weight(1f, fill = false)
    ) {}
  }
}

@Composable
@Preview(showBackground = true)
private fun FixedFooterPreview2() {
  MaterialTheme {
    Surface {
      Column() {
        FooterContent2(description = "wayway")

        FooterContent2(description = "waywaywaywaywaywaywaywaywaywaywaywaywayway")
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun FixedFooterPreview3() {
  MaterialTheme {
    Surface {
      Column() {
        FooterContent3(description = "wayway")

        FooterContent3(description = "waywaywaywaywaywaywaywaywaywaywaywaywayway")
      }
    }
  }
}
