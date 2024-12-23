package com.mataku.snippets.ui.compose.sample

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Sample(
  name = "Shared element sample",
  description = "Shared element sample",
  tags = ["Shared element"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/SharedElementScreen.kt"
)
@Composable
fun SharedElementScreen() {
  SharedTransitionLayout {
    val navController = rememberNavController()
    NavHost(
      navController = navController,
      startDestination = "list"
    ) {
      composable("list") {
        ListScreen(
          sharedTransitionScope = this@SharedTransitionLayout,
          animatedContentScope = this@composable,
        ) { item ->
          navController.navigate("details/${item.id}")
        }
      }
      composable(
        "details/{item_id}",
        arguments = listOf(navArgument("item_id") { type = NavType.IntType })
      ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("item_id") ?: return@composable
        val item = SharedElementItem.items.firstOrNull { it.id == id } ?: return@composable
        DetailsScreen(
          item = item,
          sharedTransitionScope = this@SharedTransitionLayout,
          animatedContentScope = this@composable,
          onBackPressed = navController::popBackStack,
        )
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ListScreen(
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  onItemClick: (SharedElementItem) -> Unit
) {
  val results = SharedElementItem.items
  val lastIndex = results.lastIndex
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    itemsIndexed(results) { index, item ->
      with(sharedTransitionScope) {
        SharedElementRow(
          id = item.id,
          imageRes = item.imageRes,
          title = item.name,
          description = item.description,
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              onItemClick(item)
            }
            .padding(
              horizontal = 16.dp
            ),
          sharedTransitionScope = sharedTransitionScope,
          animatedContentScope = animatedContentScope,
        )
      }
      if (index != lastIndex) {
        Divider()
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsScreen(
  item: SharedElementItem,
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  onBackPressed: () -> Unit,
) {
  with(sharedTransitionScope) {
    Column(
      modifier = Modifier
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(id = item.imageRes),
        contentDescription = item.description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .sharedElement(
            state = sharedTransitionScope.rememberSharedContentState(
              key = "shared_element_screen_image_${item.id}",
            ),
            animatedVisibilityScope = animatedContentScope,
          )
          .aspectRatio(1F)
          .fillMaxWidth()
      )

      Spacer(
        modifier = Modifier.height(24.dp)
      )

      Text(
        text = item.name,
        fontSize = 14.sp,
        color = MaterialTheme.colors.onSurface,
      )

      Spacer(
        modifier = Modifier.height(8.dp)
      )

      Text(
        text = item.description,
        fontSize = 12.sp,
        color = MaterialTheme.colors.onSecondary
      )

      Spacer(
        modifier = Modifier.height(24.dp)
      )

      Button(
        onClick = onBackPressed
      ) {
        Text(
          text = "Back!!!!!!!"
        )
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.SharedElementRow(
  id: Int,
  @DrawableRes imageRes: Int,
  title: String,
  description: String,
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
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
        .sharedElement(
          state = sharedTransitionScope.rememberSharedContentState(
            key = "shared_element_screen_image_$id",
          ),
          animatedVisibilityScope = animatedContentScope
        )
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
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier,
      )
      Spacer(
        modifier = Modifier.height(4.dp)
      )
      Text(
        text = description,
        fontSize = 12.sp,
        color = MaterialTheme.colors.onSecondary
      )
    }
  }
}

private data class SharedElementItem(
  val id: Int,
  val name: String,
  val description: String,
  @DrawableRes val imageRes: Int,
) {
  companion object {
    val items = listOf(
      SharedElementItem(
        id = 1,
        name = "Item 1",
        description = "Description 1",
        imageRes = R.drawable.kota
      ),
      SharedElementItem(
        id = 2,
        name = "Item 2",
        description = "Description 2",
        imageRes = R.drawable.ic_launcher_foreground
      ),
      SharedElementItem(
        id = 3,
        name = "Item 3",
        description = "Description 3",
        imageRes = R.drawable.ic_launcher_background
      ),

      )
  }
}
