-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*, InnerClasses
-keepattributes Signature, Exception
-keepnames class ** { *; }

-keep class kotlin.reflect.** { *; }
-keep interface kotlin.reflect.** { *; }

# Kotlin serialization
-keep,includedescriptorclasses class com.mataku.snippets.**$$serializer { *; }
-keepclassmembers class com.mataku.snippets.** {
    *** Companion;
}

# Material components
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

# AndroidX
-dontwarn androidx.**
-keep class androidx.** { *; }
-keepclassmembers class androidx.** { *; }
-keep interface androidx.* { *; }
