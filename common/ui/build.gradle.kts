plugins {
    id(BuildPlugins.Apply.androidLibrary)
    id(BuildPlugins.Apply.kotlinAndroid)
    id(BuildPlugins.Apply.kotlinKapt)
    id(BuildPlugins.Apply.daggerHiltPlugin)
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
    api(project(":domain"))
    api(project(":common:android"))

    implementation(Libraries.Kotlin.jdk)
    api(Libraries.AndroidX.appCompat)
    api(Libraries.material)
    api(Libraries.AndroidX.constraintLayout)
    api(Libraries.AndroidX.Fragment.core)
    api(Libraries.Scale.ssp)
    api(Libraries.Scale.sdp)
    api(Libraries.AndroidX.Room.sqlite)
    api(Libraries.PartUtils.formManager)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)

    /* Navigation */
    api(Libraries.AndroidX.Navigation.ui)
    api(Libraries.AndroidX.Navigation.core)

    /* Hilt */
    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)

    api(Libraries.ImageUtils.lottie)

    /*coil*/
    implementation(Libraries.ImageUtils.coil)
    implementation(Libraries.ImageUtils.glide)
    annotationProcessor(Libraries.ImageUtils.glideCompiler)
}