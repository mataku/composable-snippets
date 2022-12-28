plugins {
  id("sandbox.android.application")
  id("sandbox.android.compose")
  id("dagger.hilt.android.plugin")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.android")
}

dependencies {
  implementation(libs.activity.ktx)
  implementation(libs.material)
  implementation(libs.coroutines)
  implementation("androidx.core:core-ktx:1.9.0")

  implementation(libs.activity.compose)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.animation)
  implementation(libs.compose.material)
  implementation(libs.compose.navigation)
  implementation(libs.coil.compose)
  implementation(libs.accompanist.pager)

  implementation(libs.napier)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
  kapt(libs.hilt.android.compiler)
}

repositories {
  mavenCentral()
  google()
}
