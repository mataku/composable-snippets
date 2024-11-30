package com.mataku.snippets.ui.compose.sample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.catalog.framework.annotations.Sample
import java.io.File

@Sample(
  name = "Image upload",
  description = "Image upload",
  tags = ["Camera", "Gallery"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/jetpackcomposesandbox/ui/compose/sample/ImageUploadScreen.kt"
)
@Composable
fun ImageUploadScreen() {
  var photoFile: File? = null

  var imageUri: Uri? by remember {
    mutableStateOf(null)
  }

  val context = LocalContext.current
  val hasCameraPermission = remember {
    mutableStateOf(
      ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
      ) == PackageManager.PERMISSION_GRANTED
    )
  }

  val hasCameraPermissionCheckLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted: Boolean ->
    hasCameraPermission.value = isGranted
  }

  val takePictureLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicture()
  ) { success: Boolean ->
    if (success) {
      imageUri = Uri.fromFile(photoFile)
    }
  }

  val imageGalleryLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    imageUri = uri
  }

  val screenWidth = LocalConfiguration.current.screenWidthDp

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        16.dp
      ),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (imageUri == null) {
      Box(
        modifier = Modifier
          .width(screenWidth.dp)
          .height(screenWidth.dp)
          .background(
            color = MaterialTheme.colors.onSurface
          )
      )
    } else {
      AsyncImage(
        model = ImageRequest.Builder(context)
          .data(imageUri)
          .build(),
        contentDescription = "Uploaded image",
        modifier = Modifier
          .width(screenWidth.dp)
          .height(screenWidth.dp),
        contentScale = ContentScale.Crop
      )
    }

    Spacer(
      modifier = Modifier
        .height(
          24.dp
        )
    )

    if (hasCameraPermission.value) {
      Button(onClick = {
        photoFile = createImageFile(context)
        val cameraPhotoUri = FileProvider.getUriForFile(
          context,
          "${context.packageName}.fileprovider",
          photoFile!!
        )
        takePictureLauncher.launch(cameraPhotoUri)
      }) {
        Text(text = "Take a Photo \uD83D\uDCF7")
      }
    } else {
      Button(onClick = {
        hasCameraPermissionCheckLauncher.launch(Manifest.permission.CAMERA)
      }) {
        Text(text = "Request Camera Permission")
      }
    }

    Spacer(
      modifier = Modifier
        .height(
          16.dp
        )
    )

    Button(
      onClick = {
        imageGalleryLauncher.launch("image/*")
      }
    ) {
      Text(text = "Pick Image from Gallery")
    }
  }
}

fun createImageFile(context: Context): File {
  val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
  return File.createTempFile(
    "image_upload_${System.currentTimeMillis()}",
    ".jpg",
    storageDir
  )
}
