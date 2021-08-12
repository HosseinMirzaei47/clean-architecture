import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

fun BaseExtension.addAndroidConfigs(
    appId: String? = null,
    buildTypes: (() -> Unit)? = null
) {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.buildToolsVersion)

    defaultConfig {
        appId?.let {
            applicationId = it
        }
        testInstrumentationRunner = TestLibraries.AndroidJunitRunner

        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = Config.versionCode
        versionName = Config.versionName
        multiDexEnabled = true

        testInstrumentationRunner = TestLibraries.AndroidJunitRunner
    }

    buildTypes?.let { it() } ?: run {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                appId?.let {
                    isShrinkResources = true
                }
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
}

fun BaseExtension.addFlavours() {
    flavorDimensions("version")
    productFlavors {
        create("webApi") {
            dimension = "version"
        }
        create("mock") {
            dimension = "version"
        }
    }
}