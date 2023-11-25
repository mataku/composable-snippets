buildscript {
  repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
  }
}

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.dagger.hilt) apply false
  alias(libs.plugins.kotlin) apply false
  alias(libs.plugins.ksp)
  alias(libs.plugins.google.services) // ensure to apply at the bottom
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
