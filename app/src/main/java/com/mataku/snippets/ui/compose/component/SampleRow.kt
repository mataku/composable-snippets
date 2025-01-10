package com.mataku.snippets.ui.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mataku.snippets.R

@Composable
fun SampleRow(
  id: Int,
  @DrawableRes imageRes: Int,
  title: String,
  description: String,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .height(56.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = description,
      modifier = Modifier
        .size(48.dp)
    )

    Spacer(
      modifier = Modifier.width(16.dp)
    )

    Column(
      modifier = Modifier,
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = title,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier,
      )
      Spacer(
        modifier = Modifier.height(4.dp)
      )
      Text(
        text = description,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun SampleRowPreview() {
  MaterialTheme {
    Surface {
      SampleRow(
        id = 0,
        imageRes = R.drawable.kota,
        title = "Title",
        description = "Description"
      )
    }
  }
}
