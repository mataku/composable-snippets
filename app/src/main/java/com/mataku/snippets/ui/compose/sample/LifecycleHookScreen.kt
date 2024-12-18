package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.catalog.framework.annotations.Sample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Sample(
  name = "Lifecycle hook sample",
  description = "Hook lifecycle event on Compose without onDestroy",
  tags = ["Lifecycle"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/LifecycleHookScreen.kt"
)
@Composable
fun LifecycleHookScreen() {

  val lifecycleOwner = LocalLifecycleOwner.current
  val state = rememberLifecycleHookState()
  val logList by state.log.collectAsState()

  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      state.addLog(event.name)
    }
    lifecycleOwner.lifecycle.addObserver(observer)

    onDispose {
      lifecycleOwner.lifecycle.removeObserver(observer)
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(logList) { log ->
      Cell(log = log)
    }
  }
}

@Composable
fun rememberLifecycleHookState(): LifecycleHookState {
  return remember {
    LifecycleHookState()
  }
}

class LifecycleHookState {
  private val _log: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

  val log: MutableStateFlow<List<String>> = _log

  fun addLog(lifecycleEvent: String) {
    _log.update {
      it + "$lifecycleEvent hooked"
    }
  }
}

@Composable
private fun Cell(
  log: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = log,
    modifier = modifier
      .padding(
        horizontal = 12.dp,
        vertical = 8.dp
      ),
    color = MaterialTheme.colors.onSurface
  )
}
