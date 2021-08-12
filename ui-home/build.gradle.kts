plugins {
    id(BuildPlugins.Apply.androidLibrary)
    id(BuildPlugins.Apply.kotlinAndroid)
    id(BuildPlugins.Apply.daggerHiltPlugin)
    id(BuildPlugins.Apply.safeArgsKotlinPlugin)
    id(BuildPlugins.Apply.kotlinKapt)
}

kapt {
    correctErrorTypes = true
}

android {
    addAndroidConfigs()
    addFlavours()

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    @Suppress("UnstableApiUsage")
    buildFeatures.dataBinding = true
}


dependencies {

    implementation(project(":common-ui"))
    implementation(Libraries.Epoxy.dataBinding)
    api(Libraries.Epoxy.core)
    kapt(Libraries.Epoxy.processor)

    /* Hilt */
    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)

    implementation(Libraries.AndroidX.browser)

}