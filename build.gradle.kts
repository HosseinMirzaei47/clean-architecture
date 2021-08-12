// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven {
            url = uri("https://localartifactory.jfrog.io/artifactory/local/")
            artifactUrls("https://localartifactory.jfrog.io/artifactory/local/")
            credentials {
                username = "android-user"
                password = "p9PTCB[^Z}tcM}wn:ZF4NnVNd=DXC-ML=w3/6"
            }
        }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath(BuildPlugins.GradleClassPath.androidGradlePlugin)
        classpath(BuildPlugins.GradleClassPath.kotlinGradlePlugin)
        classpath(BuildPlugins.GradleClassPath.hiltGradlePlugin)
        classpath(BuildPlugins.GradleClassPath.navSafeArgs)
        classpath(BuildPlugins.GradleClassPath.firebaseClassPath)
        classpath(BuildPlugins.GradleClassPath.firebaseCrashlytics)
        classpath(BuildPlugins.GradleClassPath.kotlinSerialization)
    }
}

allprojects {
    repositories {
        maven {
            url = uri("https://localartifactory.jfrog.io/artifactory/local/")
            artifactUrls("https://localartifactory.jfrog.io/artifactory/local/")
            credentials {
                username = "android-user"
                password = "p9PTCB[^Z}tcM}wn:ZF4NnVNd=DXC-ML=w3/6"
            }
        }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}