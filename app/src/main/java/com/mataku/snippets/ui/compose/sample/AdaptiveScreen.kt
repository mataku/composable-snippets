@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.mataku.snippets.ui.compose.sample

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.compose.component.SampleRow

@Sample(
  name = "Adaptive Screen sample",
  description = "Adaptive Screen sample",
  tags = ["adoptive", "tablet"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/AdaptiveScreen.kt"
)
@Composable
fun AdaptiveScreen() {
  val navigator = rememberListDetailPaneScaffoldNavigator<SampleItem>()
  val results = SampleItem.items

  BackHandler(navigator.canNavigateBack()) {
    navigator.navigateBack()
  }

  ListDetailPaneScaffold(
    directive = navigator.scaffoldDirective,
    value = navigator.scaffoldValue,
    listPane = {
      AnimatedPane {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
          itemsIndexed(results) { index, item ->
            SampleRow(
              id = index,
              imageRes = item.imageRes,
              title = item.name,
              description = item.description,
              modifier = Modifier
                .fillMaxSize()
                .clickable {
                  navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                }
            )
          }
        }
      }
    },
    detailPane = {
      navigator.currentDestination?.content?.let { sampleItem ->
        AnimatedPane {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(
                16.dp
              ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Image(
              painter = painterResource(id = sampleItem.imageRes),
              contentDescription = sampleItem.description,
              modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
            )
            Spacer(
              modifier = Modifier.height(24.dp)
            )

            Text(
              text = sampleItem.name,
              modifier = Modifier
            )
            Spacer(
              modifier = Modifier.height(8.dp)
            )
            Text(
              text = sampleItem.description,
              modifier = Modifier
            )
          }
        }
      }
    }
  )
}
