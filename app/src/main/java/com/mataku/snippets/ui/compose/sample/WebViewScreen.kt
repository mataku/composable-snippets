package com.mataku.snippets.ui.compose.sample

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.webkit.WebViewCompat
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.android.catalog.framework.annotations.Sample

// Make WebView full screen
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Sample(
  name = "WebView Sample",
  description = "WebView",
  tags = ["WebView"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/WebViewScreen.kt"
)
@Composable
fun WebViewScreen() {
  val state = rememberWebViewState(
    url = "https://mataku.today"
  )
  var webViewInfoDisplayEnabled by remember {
    mutableStateOf(false)
  }

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    Scaffold(
      topBar = {},
      floatingActionButton = {
        FloatingActionButton(
          onClick = {
            webViewInfoDisplayEnabled = true
          }
        ) {
          Icon(
            Icons.Filled.Info,
            contentDescription = "settings button"
          )
        }
      }
    ) {
      WebView(
        state = state,
        onCreated = { it.settings.javaScriptEnabled = true }
      )
    }
  }
  if (webViewInfoDisplayEnabled) {
    Dialog(
      onDismissRequest = {
        webViewInfoDisplayEnabled = false
      },
    ) {
      Surface {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Info(
            title = "URL",
            content = state.lastLoadedUrl ?: ""
          )
          Divider()
          val webViewPackage = WebViewCompat.getCurrentWebViewPackage(
            LocalContext.current
          )
          Info(
            title = "WebView implementation",
            content = webViewPackage?.packageName ?: ""
          )
          Divider()
          Info(
            title = "WebView implementation version name",
            content = webViewPackage?.versionName ?: ""
          )
          Divider()
          Info(
            title = "WebView implementation long version code",
            content = webViewPackage?.longVersionCode.toString()
          )
          Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1F))
            Text(
              text = "OK",
              color = MaterialTheme.colors.primary,
              modifier = Modifier
                .clickable {
                  webViewInfoDisplayEnabled = false
                }
                .padding(8.dp),
              fontSize = 14.sp
            )
          }
        }
      }
    }
  }
}

@Composable
private fun Info(
  title: String,
  content: String
) {
  Column {
    Text(
      text = title,
      textAlign = TextAlign.Start,
      fontWeight = FontWeight.Normal,
      fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = content,
      fontSize = 14.sp,
      color = Color.Gray
    )
  }
}
