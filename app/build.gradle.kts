plugins {
  id("sandbox.android.application")
  id("sandbox.android.compose")
  id("dagger.hilt.android.plugin")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp") version ("1.8.10-1.0.9")
}

android {
  namespace = "com.mataku.snippets"
}

kotlin {
  jvmToolchain(11)
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
  implementation(libs.constraintlayout.compose)

  implementation(libs.napier)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
  kapt(libs.hilt.android.compiler)

  implementation(libs.casa)
  ksp(libs.casa.processor)
}

repositories {
  mavenCentral()
  google()
}
