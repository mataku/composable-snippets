buildscript {
  repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
  }
  dependencies {
    classpath(libs.android.gradle.plugin)
    classpath(libs.kotlin.gradle.plugin)
    classpath(libs.hilt.gradle.plugin)

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

plugins {
  id("com.google.gms.google-services") version "4.3.15" apply false
  id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
