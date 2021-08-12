plugins {
    id(BuildPlugins.Apply.javaLibrary)
    id(BuildPlugins.Apply.kotlin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    api(Libraries.Kotlin.jdk)

    /* Coroutines */
    api(Libraries.Kotlin.Coroutine.core)
    api(Libraries.Kotlin.Coroutine.android)
    api(Libraries.AndroidX.LifeCycle.liveData)

    /* For instantiating AlarmManager in TaskAlarmManager in data layer */
    api(Libraries.AndroidX.ktxCore)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.testRunner)
}