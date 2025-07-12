package ext

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

fun Project.composeConfiguration() {
  with(pluginManager) {
    apply("org.jetbrains.kotlin.plugin.compose")
  }

  if (this.name == "app") {
    extensions.configure<BaseAppModuleExtension> {
      buildFeatures {
        compose = true
      }

      with(extensions.getByType<KotlinAndroidProjectExtension>()) {
        compilerOptions {
          jvmTarget.set(JvmTarget.JVM_17)
          // painful but too many files target
          freeCompilerArgs.add("-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi")
        }
      }
      with(extensions.getByType<ComposeCompilerGradlePluginExtension>()) {
        val composeReportEnabled =
          rootProject.providers.gradleProperty("composeCompilerReports").orNull == "true"

        if (composeReportEnabled) {
          reportsDestination.set(layout.buildDirectory.dir("compose_reports"))
          metricsDestination.set(layout.buildDirectory.dir("compose_metrics"))
        }
      }
    }
  }
}
