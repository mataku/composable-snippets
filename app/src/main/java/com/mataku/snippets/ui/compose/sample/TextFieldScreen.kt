package com.mataku.snippets.ui.compose.sample

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.catalog.framework.annotations.Sample
import com.mataku.snippets.ui.Colors
import com.mataku.snippets.ui.compose.component.Title

@Sample(
  name = "TextField sample",
  description = "text field",
  tags = ["TextField"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/TextFieldScreen.kt"
)
@Composable
fun TextFieldScreen() {
  var simpleText by remember {
    mutableStateOf("")
  }
  var outlinedText by remember {
    mutableStateOf("")
  }
  var sensitiveText by remember {
    mutableStateOf("")
  }
  var passwordVisible by remember {
    mutableStateOf(false)
  }
  var textCounter by remember {
    mutableStateOf("")
  }
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = 16.dp
      ),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    SimpleTextFieldSample(
      text = simpleText,
      onValueChange = {
        simpleText = it
      },
      modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextFieldSample(
      text = outlinedText,
      onValueChange = {
        outlinedText = it
      },
      modifier = Modifier.fillMaxWidth()
    )

    SensitiveTextFieldSample(
      text = sensitiveText,
      onValueChange = {
        sensitiveText = it
      },
      passwordVisible = passwordVisible,
      onPasswordVisibilityChanged = {
        passwordVisible = !passwordVisible
      },
      modifier = Modifier.fillMaxWidth()
    )

    TextCountFieldSample(
      text = textCounter,
      onValueChange = {
        textCounter = it
      },
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun TextFieldScreenPreview() {
  MaterialTheme {
    Surface {
      TextFieldScreen()
    }
  }
}

@Composable
private fun SimpleTextFieldSample(
  text: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Title(label = "Simple")
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = text,
      onValueChange = onValueChange,
      modifier = Modifier.fillMaxWidth(),
      label = {
        Text(text = "username")
      },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
      singleLine = true
    )
  }
}

@Composable
private fun OutlinedTextFieldSample(
  text: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Title(label = "Outlined")
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
      value = text,
      onValueChange = onValueChange,
      modifier = Modifier.fillMaxWidth(),
      label = {
        Text(text = "username")
      },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
      singleLine = true
    )
  }
}

@Composable
private fun SensitiveTextFieldSample(
  text: String,
  onValueChange: (String) -> Unit,
  passwordVisible: Boolean,
  onPasswordVisibilityChanged: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Title(label = "Sensitive text")
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = text,
      onValueChange = onValueChange,
      label = {
        Text("password")
      },
      visualTransformation = if (passwordVisible) {
        VisualTransformation.None
      } else {
        PasswordVisualTransformation()
      },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Next
      ),
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
      trailingIcon = {
        if (passwordVisible) {
          IconButton(onClick = onPasswordVisibilityChanged) {
            Icon(
              imageVector = Icons.Filled.Visibility,
              contentDescription = "password visibility toggle"
            )
          }
        } else {
          IconButton(onClick = onPasswordVisibilityChanged) {
            Icon(
              imageVector = Icons.Filled.VisibilityOff,
              contentDescription = "password visibility toggle"
            )
          }
        }
      }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextCountFieldSample(
  text: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Title(label = "text counter")
    Spacer(modifier = Modifier.height(8.dp))
    Box(
      modifier = modifier
        .border(
          width = 1.dp,
          shape = RoundedCornerShape(4.dp),
          color = MaterialTheme.colors.onSecondary
        )

    ) {
      ConstraintLayout(modifier = modifier) {
        val (textField, textCounter) = createRefs()

        BasicTextField(
          value = text,
          onValueChange = onValueChange,
          modifier = Modifier
            .constrainAs(textField) {
              top.linkTo(parent.top)
              start.linkTo(parent.start)
              end.linkTo(parent.end)
              bottom.linkTo(textCounter.top)
              width = Dimension.fillToConstraints
            }
            .defaultMinSize(minHeight = TextFieldDefaults.MinHeight),
          decorationBox = { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
              placeholder = {
                Text(text = "Type your story...")
              },
              innerTextField = innerTextField,
              enabled = true,
              singleLine = true,
              visualTransformation = VisualTransformation.None,
              interactionSource = remember {
                MutableInteractionSource()
              },
              value = text
            )
          }
        )

        Text(
          text = text.length.toString(),
          color = Colors.textSecondary,
          modifier = Modifier
            .padding(end = 16.dp, bottom = 12.dp)
            .constrainAs(textCounter) {
              top.linkTo(textField.bottom)
              centerHorizontallyTo(parent)
              bottom.linkTo(parent.bottom)
              width = Dimension.fillToConstraints
            },
          textAlign = TextAlign.End
        )
      }
    }
  }
}
