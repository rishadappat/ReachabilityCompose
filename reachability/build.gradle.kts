plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val PUBLISH_GROUP_ID by extra { "in.appat" }
val PUBLISH_VERSION by extra { "0.1.0" }
val PUBLISH_ARTIFACT_ID by extra { "ReachabilityCompose" }
val PUBLISH_DESCRIPTION by extra { "A jetpack compose library which will help the user to listen for the network changes and show a message if needed." }
val PUBLISH_URL by extra { "https://github.com/rishadappat/ReachabilityCompose" }
val PUBLISH_LICENSE_NAME by extra { "Apache License" }
val PUBLISH_LICENSE_URL by extra { "https://github.com/rishadappat/ReachabilityCompose/blob/main/LICENSE" }
val PUBLISH_DEVELOPER_ID by extra { "rishadappat" }
val PUBLISH_DEVELOPER_NAME by extra { "Rishad Appat" }
val PUBLISH_DEVELOPER_EMAIL by extra { "rishadappat@gmail.com" }
val PUBLISH_SCM_CONNECTION by extra { "scm:git:github.com/rishadappat/ReachabilityCompose.git" }
val PUBLISH_SCM_DEVELOPER_CONNECTION by extra { "scm:git:ssh://github.com/rishadappat/ReachabilityCompose.git" }
val PUBLISH_SCM_URL by extra { "https://github.com/rishadappat/ReachabilityCompose/tree/main" }

apply {
    from("${rootProject.projectDir}/scripts/publish-module.gradle")
}

android {
    namespace = "com.appat.connectivity"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    val composeBom = platform("androidx.compose:compose-bom:2023.06.01")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation ("androidx.compose.ui:ui")
}