import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id(BuildPlugins.Apply.androidLibrary)
    id(BuildPlugins.Apply.kotlinAndroid)
    id(BuildPlugins.Apply.kotlinKapt)
    id(BuildPlugins.Apply.daggerHiltPlugin)
    id(BuildPlugins.Apply.protoBufPlugin) version BuildPlugins.Apply.protoBufVersion
}

android {
    addAndroidConfigs()
    addFlavours()
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

dependencies {

    api(project(":common-android"))
    implementation(project(":data-model"))
    implementation(project(":domain"))

    /* Retrofit */
    api(Libraries.Network.Retrofit.core)
    api(Libraries.Network.Retrofit.moshiConverter)
    api(Libraries.Network.OkHttp.core)
    api(Libraries.Serializable.gson)
    api(Libraries.Network.OkHttp.logger)

    /* Hilt */
    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)

    /* Room - Annotation processor. model:android module apis other dependencies */
    kapt(Libraries.AndroidX.Room.compiler)

    /* DataStore */
    api(Libraries.AndroidX.DataStore.dataStore)
    api(Libraries.AndroidX.DataStore.dataStoreCore)
    api(Libraries.AndroidX.DataStore.javaLite)
    api(Libraries.AndroidX.DataStore.dataStoreProtoArtifact)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)

}

protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = Libraries.AndroidX.DataStore.dataStoreProtoArtifact
    }
    generateProtoTasks {
        // all() returns the collection of all protoc tasks
        all().forEach { task ->
            // Here you can configure the task
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
