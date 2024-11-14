plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.reclamation"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.reclamation"
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

    android {
        packaging {
            resources {
                excludes.add("/META-INF/DEPENDENCIES")
            }
        }
    }

}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ( "com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.twilio.sdk:twilio:7.17.0")
    implementation ("com.google.guava:guava:30.1-jre")
    implementation ("com.google.android.material:material:1.12.0")
}