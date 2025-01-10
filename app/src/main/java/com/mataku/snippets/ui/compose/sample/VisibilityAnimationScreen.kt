package com.mataku.snippets.ui.compose.sample

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.R

@OptIn(ExperimentalFoundationApi::class)
@Sample(
  name = "Visibility Animation sample",
  description = "Visibility Animation",
  tags = ["Visibility", "Animation"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/VisibilityAnimationScreen.kt"
)
@Composable
fun VisibilityAnimationScreen() {
  val requester = remember {
    BringIntoViewRequester()
  }
  val numberPattern = Regex("^\\d+\$")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = 16.dp,
        vertical = 24.dp,
      )
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    var visible by remember {
      mutableStateOf(true)
    }
    var visibilityAnimationDuration by remember {
      mutableIntStateOf(1000)
    }
    val height = LocalConfiguration.current.screenWidthDp.dp - 32.dp
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(height)
    ) {
      androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
          animationSpec = tween(visibilityAnimationDuration),
        ),
        exit = fadeOut(
          animationSpec = tween(visibilityAnimationDuration),
        ),
        label = "image_transition",
        content = {
          Image(
            painter = painterResource(id = R.drawable.kota),
            contentDescription = "Kota",
            modifier = Modifier
              .fillMaxWidth()
              .aspectRatio(1F)
          )
        }
      )
    }
    Spacer(modifier = Modifier.height(24.dp))

    OutlinedTextField(
      value = visibilityAnimationDuration.toString(),
      onValueChange = { value ->
        if (value.matches(numberPattern)) {
          visibilityAnimationDuration = value.toIntOrNull() ?: 0
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .bringIntoViewRequester(requester),
      label = {
        Text(text = "Visibility Animation Duration (ms)")
      },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
      ),
      singleLine = true
    )

    Spacer(
      modifier = Modifier.height(24.dp)
    )

    Button(
      onClick = {
        visible = !visible
      },
      modifier = Modifier
        .width(120.dp),
    ) {
      Text(
        text = if (visible) "Hide" else "Show",
        fontSize = 16.sp,
        modifier = Modifier.padding(
          horizontal = 12.dp,
          vertical = 8.dp
        )
      )
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun VisibilityAnimationScreenPreview() {
  MaterialTheme {
    Surface {
      VisibilityAnimationScreen()
    }
  }
}
