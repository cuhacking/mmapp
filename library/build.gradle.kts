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
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    js(BOTH) {
        browser()
    }
    android()
    iosX64("ios") {}

    explicitApi()

    cocoapods {
        summary = "mmapp framework"
        homepage = "https://github.com/cuhacking/mmapp"

        frameworkName = "Mmapp"

        ios.deploymentTarget = "14.0"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.github.dellisd.spatialk:geojson:0.0.3-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.github.dellisd.spatialk:geojson-dsl:0.0.3-SNAPSHOT")
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting
        val iosTest by getting
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

android {
    compileSdkVersion(30)
    sourceSets {
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDir("src/androidMain/kotlin")
        }
        val test by getting {
            java.srcDir("src/androidTest/kotlin")
        }
    }
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}