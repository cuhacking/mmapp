plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.cuhacking.mmapp")
}

group = "com.cuhacking.mmapp"
version = "0.1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    js(BOTH) {
        browser()
    }
    android()
    iosX64("ios") {}

    cocoapods {
        summary = "mmapp framework"
        homepage = "https://github.com/cuhacking/mmapp"

        frameworkName = "Mmapp"

        ios.deploymentTarget = "14.0"
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}