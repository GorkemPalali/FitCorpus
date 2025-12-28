plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.fitcorpus"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fitcorpus"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // API Base URL - Production
        buildConfigField("String", "API_BASE_URL", "\"https://api.fitcorpus.com/\"")
    }

    buildTypes {
        debug {
            // Development/Testing için localhost veya test server
            // Android Emulator için: http://10.0.2.2:PORT/
            // Gerçek cihaz için: http://YOUR_LOCAL_IP:PORT/
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080/\"")
            // Eğer production backend'i test ediyorsanız:
            // buildConfigField("String", "API_BASE_URL", "\"https://api.fitcorpus.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Production URL zaten defaultConfig'de tanımlı
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

// Exclude Moshi Kapt codegen from Kapt configurations since we're using KSP
configurations.named("kapt") {
    exclude(group = "com.squareup.moshi", module = "moshi-kotlin-codegen")
}

ksp {
    arg("moshi.generateProguardRules", "true")
}

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    // ViewModel
    implementation(libs.lifecycle.viewmodel.compose)
    
    // Navigation
    implementation(libs.navigation.compose)
    
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler) {
        exclude(group = "com.squareup.moshi", module = "moshi-kotlin-codegen")
    }
    implementation(libs.hilt.navigation.compose)
    
    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler) {
        exclude(group = "com.squareup.moshi", module = "moshi-kotlin-codegen")
    }
    
    // DataStore
    implementation(libs.datastore.preferences)
    implementation(libs.encrypted.preferences)
    
    // WorkManager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)
    kapt(libs.hilt.work.compiler) {
        exclude(group = "com.squareup.moshi", module = "moshi-kotlin-codegen")
    }
    
    // Coil
    implementation(libs.coil.compose)
    
    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended")
    
    // Logging
    implementation(libs.timber)
    
    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockwebserver)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}