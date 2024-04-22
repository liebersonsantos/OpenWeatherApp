import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.liebersonsantos.openweatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.liebersonsantos.openweatherapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val keystoreFile = project.rootProject.file("key.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val apiKey = properties.getProperty("API_KEY") ?: ""
        val baseUrl = properties.getProperty("BASE_URL") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = baseUrl
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.mock)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.hilt.android)
    implementation(libs.hilt.android.navigation)
    kapt(libs.hilt.android.compiler)

    implementation(libs.splash.screen)

    implementation(libs.navigation.compose)

    //service location
    implementation(libs.services.location)

    testImplementation(libs.junit)

    //unit test
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    //android test
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}