plugins {
  `kotlin-dsl`
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  implementation(libs.android.gradle.plugin)
  implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
  plugins {
    register("androidApplication") {
      id = "sandbox.android.application"
      implementationClass = "ApplicationConventionPlugin"
    }
    register("androidCompose") {
      id = "sandbox.android.compose"
      implementationClass = "ComposeConventionPlugin"
    }
  }
}
