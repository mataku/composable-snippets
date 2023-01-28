package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.catalog.framework.annotations.Sample

@Sample(
  name = "FixedFooter",
  description = "fixed footer"
)
@Composable
fun FixedFooter() {
  Box(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
    FooterContent(
      title = "title",
      description = "description"
    )
  }
}

@Composable
private fun FooterContent(
  title: String,
  description: String
) {
  ConstraintLayout(
    modifier = Modifier
      .fillMaxWidth()
      .height(40.dp)
  ) {
    val (titleRef, subTextRef, spacerRef, endIconRef) = createRefs()
    Text(
      text = title,
      fontSize = 16.sp,
      modifier = Modifier
        .constrainAs(titleRef) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(parent.start)
          end.linkTo(subTextRef.start)
          width = Dimension.wrapContent
        }
    )
    Row(
      modifier = Modifier
        .constrainAs(subTextRef) {
          top.linkTo(parent.top)
          bottom.linkTo(parent.bottom)
          start.linkTo(titleRef.end)
          end.linkTo(spacerRef.start)
          width = Dimension.fillToConstraints
        },
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = description,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .defaultMinSize(16.dp)
          .background(color = Color.Gray)
          .weight(1f, fill = false),
        softWrap = false
      )

      Box(
        modifier = Modifier
          .size(14.dp)
          .clip(CircleShape)
          .background(color = Color.Black)
          .defaultMinSize(14.dp)
      )
    }

    Spacer(modifier = Modifier
      .constrainAs(spacerRef) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(subTextRef.end)
        end.linkTo(endIconRef.start)
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
        .constrainAs(endIconRef) {
          end.linkTo(parent.end)
          top.linkTo(parent.top)
          start.linkTo(spacerRef.end)
          bottom.linkTo(parent.bottom)
          horizontalChainWeight = 1f
          width = Dimension.wrapContent
        }
    ) {}
  }
}

@Composable
@Preview(showBackground = true)
private fun FixedFooterPreview() {
  MaterialTheme {
    Surface {
      Column() {
        FooterContent(
          title = "title",
          description = "wayway"
        )

        FooterContent(
          title = "title",
          description = "waywaywaywaywaywaywaywaywaywaywaywaywayway"
        )
      }
    }
  }
}
