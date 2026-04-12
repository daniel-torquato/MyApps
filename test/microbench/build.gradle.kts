plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.benchmark)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.symbol.processor)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "xyz.torquato.myapps.test.microbench"
    compileSdk = 36

    defaultConfig {
        minSdk = 35

        testInstrumentationRunner = "xyz.torquato.myapps.test.microbench.HiltBenchmarkRunner"
    }
    lint {
        targetSdk = 36
    }
    testOptions {
        targetSdk = 36
    }
    testBuildType = "release"
    buildTypes {
        debug {
            // Since isDebuggable can"t be modified by gradle for library modules,
            // it must be done in a manifest - see src/androidTest/AndroidManifest.xml
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-proguard-rules.pro"
            )
        }
        release {
            isDefault = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.runner)
    implementation(libs.androidx.benchmark.junit4)
    implementation(libs.dagger.hilt.android.test)
    //implementation(project(":domain:impl"))
    //implementation(project(":domain:di"))

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.dagger.hilt.android)
    androidTestImplementation(libs.dagger.hilt.android.test)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.benchmark.junit4)
   // androidTestImplementation(project(":presentation:viewmodel"))
    implementation(project(":data:impl"))
    implementation(project(":data:di"))
    implementation(project(":domain:api"))
    implementation(project(":domain:di"))
    implementation(project(":domain:impl"))

    //androidTestImplementation(project(":data:di"))
    androidTestImplementation(project(":domain:impl"))
    androidTestImplementation(project(":presentation:viewmodel"))
    implementation(project(":presentation:viewmodel"))

    //androidTestImplementation(project(":domain:impl"))
    //androidTestImplementation(project(":domain:di"))
    // Add your dependencies here. Note that you cannot benchmark code
    // in an app module this way - you will need to move any code you
    // want to benchmark to a library module:
    // https://developer.android.com/studio/projects/android-library#Convert
    ksp(libs.dagger.hilt.compiler)
}