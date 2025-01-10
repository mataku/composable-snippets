package com.mataku.snippets.ui.compose.sample

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.android.catalog.framework.annotations.Sample
import kotlinx.coroutines.launch

@Sample(
  name = "Navigation Stack sample",
  description = "Navigation Stack sample",
  tags = ["navigation", "back stack"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/NavigationStackScreen.kt"
)
@Composable
fun NavigationStackScreen() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = "navigation_stack_root",
    enterTransition = {
      fadeIn(tween(250))
    },
    exitTransition = {
      fadeOut(tween(250))
    },
  ) {
    navigation(
      route = "navigation_stack_root",
      startDestination = "navigation_stack_home"
    ) {
      composable("navigation_stack_home") {
        NavigationStackHome(
          navigateToDetail = {
            navController.navigate("navigation_stack_detail")
          },
          navigateToDetailWithoutBackstack = {
            navController.navigate("navigation_stack_detail") {
              popUpTo("navigation_stack_home") {
                inclusive = true
              }
            }
          },
        )
      }
      composable("navigation_stack_detail") {
        NavigationStackDetail(
          navigateToHome = navController::popBackStack,
          canBack = navController.previousBackStackEntry != null
        )
      }
    }
  }
}

@Composable
private fun NavigationStackHome(
  navigateToDetail: () -> Unit,
  navigateToDetailWithoutBackstack: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        16.dp
      ),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Home",
      fontWeight = FontWeight.Medium,
      fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(24.dp))

    OutlinedButton(
      onClick = navigateToDetail
    ) {
      Text(
        text = "Navigate to Detail"
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    OutlinedButton(
      onClick = navigateToDetailWithoutBackstack
    ) {
      Text(
        text = "Navigate to Detail without backstack"
      )
    }
  }
}

@Composable
private fun NavigationStackDetail(
  navigateToHome: () -> Unit,
  canBack: Boolean,
  modifier: Modifier = Modifier
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Scaffold(
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState
      )
    },
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          it
        )
        .padding(
          vertical = 16.dp
        ),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "Detail",
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
      )

      Spacer(modifier = Modifier.height(24.dp))

      OutlinedButton(
        onClick = {
          if (canBack) {
            navigateToHome.invoke()
          } else {
            scope.launch {
              snackbarHostState.showSnackbar("No backstack!")
            }
          }
        }
      ) {
        Text(
          text = "Navigate to Home"
        )
      }
    }
  }
}

@Composable
private fun BackstackCell(
  route: String,
  id: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RectangleShape)
      .border(
        width = 0.5.dp,
        color = MaterialTheme.colorScheme.onSurface,
      )
      .padding(
        8.dp
      )
  ) {
    Column(modifier = modifier) {
      Text(
        text = route,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "id: $id",
        fontSize = 12.sp
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun BackstackPreview() {
  MaterialTheme {
    Surface {
      Box(
        modifier = Modifier
          .size(360.dp)
      ) {
        BackstackCell(
          route = "navigation_stack_home",
          id = "1",
        )
      }
    }
  }
}
