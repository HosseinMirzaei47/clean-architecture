# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.part.model.* { *; }
-keep class com.part.model_android.* { *; }

-keepnames class * extends android.os.Parcelable

# Serialization
-keepattributes InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class ir.partsoftware.cup.**$$serializer { *; }
-keepclassmembers class ir.partsoftware.cup.** {
    *** Companion;
}
-keepclasseswithmembers class ir.partsoftware.cup.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# SqlCipher
-keep, includedescriptorclasses class net.sqlcipher.** { *; }
-keep, includedescriptorclasses interface net.sqlcipher.** { *; }