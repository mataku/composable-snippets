plugins {
  id("sandbox.android.application")
  id("sandbox.android.compose")
  id("dagger.hilt.android.plugin")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.mataku.snippets"
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  implementation(libs.activity.ktx)
  implementation(libs.material)
  implementation(libs.coroutines)
  implementation(libs.androidx.core)

  implementation(libs.activity.compose)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.animation)
  implementation(libs.compose.material)
  implementation(libs.compose.navigation)
  implementation(libs.coil.compose)
  implementation(libs.accompanist.webview)
  implementation(libs.constraintlayout.compose)
  implementation(libs.compose.material.icons.extended)

  implementation(libs.napier)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
  kapt(libs.hilt.android.compiler)

  implementation(libs.casa)
  ksp(libs.casa.processor)

  implementation(libs.kotlinx.collection)
  implementation(libs.webkit)

  implementation(platform(libs.firebase.bom))
}

repositories {
  mavenCentral()
  google()
}
