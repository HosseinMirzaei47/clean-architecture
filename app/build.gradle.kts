plugins {
    id(BuildPlugins.Apply.androidApplication)
    id(BuildPlugins.Apply.kotlinAndroid)
    id(BuildPlugins.Apply.kotlinKapt)
    id(BuildPlugins.Apply.daggerHiltPlugin)
    id(BuildPlugins.Apply.firebasePlugin)
    id(BuildPlugins.Apply.crashlyticsPlugin)
    id(BuildPlugins.Apply.safeArgsKotlinPlugin)
}
kapt {
    correctErrorTypes = true
    useBuildCache = true

    javacOptions {
        option("-Xmaxerrs", 2000)
    }
}

android {
    addAndroidConfigs(Config.applicationId)
    addFlavours()

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    @Suppress("UnstableApiUsage")
    buildFeatures.dataBinding = true
}

dependencies {
    implementation(project(":ui-home"))
    implementation(project((":data")))
    implementation(project((":common-android")))
    implementation(project((":common-ui")))
    implementation(project((":shared")))
    implementation(project(":data-model"))
    implementation(project((":model")))

    // multidex
    implementation(Libraries.multidex)

    // hilt
    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)
    implementation(Libraries.AndroidX.Hilt.work)
    kapt(Libraries.AndroidX.Hilt.compiler)

    // firebase
    implementation(Libraries.Firebase.analytics)
    implementation(Libraries.Firebase.crashlytics)
    implementation(Libraries.Firebase.cloudMessaging)
    implementation(platform(Libraries.Firebase.libraryPlatform))

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)

    implementation(Libraries.AndroidX.worker)
}