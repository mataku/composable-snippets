import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import ext.kotlinConfiguration
import ext.testConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
      }

      extensions.configure<BaseAppModuleExtension> {
        compileSdk = 34
        defaultConfig {
          minSdk = 29
        }
        defaultConfig.targetSdk = 34
        signingConfigs {
          getByName("debug") {
            storeFile = file("../debug.keystore")
          }
        }
        buildTypes {
          getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
          }
        }
        kotlinConfiguration()
        packaging {
          val excludePatterns = listOf(
            "META-INF/atomicfu.kotlin_module",
            "META-INF/kotlinx-coroutines-io.kotlin_module",
            "META-INF/kotlinx-io.kotlin_module",
            "META-INF/ktor-client-json.kotlin_module",
            "META-INF/ktor-client-core.kotlin_module",
            "META-INF/ktor-http.kotlin_module",
            "META-INF/ktor-utils.kotlin_module",
            "META-INF/kotlinx-coroutines-core.kotlin_module",
            "META-INF/kotlinx-serialization-runtime.kotlin_module",
            "META-INF/gradle/incremental.annotation.processors"
          )
          resources.excludes.addAll(excludePatterns)
        }
        defaultConfig {
          applicationId = "com.mataku.snippets"
          versionCode = 1
          versionName = "1.0.0"
          proguardFiles(
            getDefaultProguardFile("proguard-android.txt"),
            "proguard-rules.pro"
          )
        }
        testConfiguration()
      }
    }
  }
}
