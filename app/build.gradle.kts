plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.produitsprimairesf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.produitsprimairesf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material) // Assurez-vous que ceci pointe vers la dernière version
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.google.zxing:core:3.4.1") // Pour la génération du QR Code
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0") // Pour scanner le QR Code sur Android

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Ajoute explicitement la dépendance pour MaterialComponents si nécessaire
    implementation("com.google.android.material:material:1.9.0") // Dernière version stable de MaterialComponents
}
