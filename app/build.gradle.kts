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
        versionName = "6.4.3"
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


    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions("api")
    productFlavors {
        create("prod") {
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")
            buildConfigField("String", "BASE_URL", "\"https://partner-bff.sendyit.com/api/v1/\"")
        }

        create("staging") {
            buildConfigField("String", "ENVIRONMENT", "\"staging\"")
            buildConfigField("String", "BASE_URL", "\"https://partner-bff-test.sendyit.com/api/v1/\"")
        }
    }
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("io.vertx:vertx-codegen:1.9.0:processor")

    //hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
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

    testImplementation ("junit:junit:4+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}
