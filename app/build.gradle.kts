import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidSdk.compile
    buildToolsVersion = "32.0.0"

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility =  JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    val key: String = gradleLocalProperties(rootDir).getProperty("WEATHER_API_KEY")

    flavorDimensions("api")
    productFlavors {
        create("prod") {
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")
            buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "API_KEY", key)
        }

        create("staging") {
            buildConfigField("String", "ENVIRONMENT", "\"staging\"")
            buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "API_KEY", key)
        }
    }
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Network Response dependency
    implementation("com.github.haroldadmin:NetworkResponseAdapter:4.0.1")

    // Lifecycle dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")
    implementation("androidx.lifecycle:lifecycle-service:2.6.1")

    //Google play services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Logging dependency
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.datadoghq:dd-sdk-android:1.15.0")

    testImplementation ("junit:junit:4+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}

