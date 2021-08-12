plugins {
    id(BuildPlugins.Apply.androidLibrary)
    id(BuildPlugins.Apply.kotlinAndroid)
    id(BuildPlugins.Apply.kotlinKapt)
    id(BuildPlugins.Apply.kotlinParcelize)
    id(BuildPlugins.Apply.kotlinxSerialization)
}

android {
    addAndroidConfigs()
    addFlavours()

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    @Suppress("UnstableApiUsage")
    buildFeatures.dataBinding = true
}


dependencies {
    api(project(":shared"))

    implementation(Libraries.Kotlin.jdk)
    implementation(Libraries.AndroidX.ktxCore)
    testImplementation(TestLibraries.junit4)

    api(Libraries.Serializable.kotlinxSerialization)

    /* Retrofit */
    api(Libraries.Network.Retrofit.gsonConverter)
    api(Libraries.Serializable.gson)

    /* Room */
    api(Libraries.AndroidX.Room.runtime)
    api(Libraries.AndroidX.Room.core)
    kapt(Libraries.AndroidX.Room.compiler)

}
