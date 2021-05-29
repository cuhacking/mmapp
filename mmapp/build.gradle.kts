import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library") version "7.0.0-beta03"
    id("com.cuhacking.mmapp")
    id("org.jetbrains.dokka")
}

group = "com.cuhacking.mmapp"
version = "0.1.1-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    js(BOTH) {
        browser()
    }
    android {
        publishLibraryVariants("release", "debug")
    }
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
                api("io.github.dellisd.spatialk:geojson:0.0.4-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Temporarily disable lint due to false positives on Map.forEach
    lintOptions {
        isAbortOnError = false
    }
}

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("mmapp")
    // TODO: There's probably a better way to do this...
    dokkaSourceSets {
        val commonMain by creating {
            sourceRoot(path = kotlin.sourceSets.getByName("commonMain").kotlin.srcDirs.first().toString())
        }
        val androidMain by creating {
            dependsOn(commonMain)
            sourceRoot(path = kotlin.sourceSets.getByName("androidMain").kotlin.srcDirs.first().toString())
        }
        val iosMain by creating {
            dependsOn(commonMain)
            sourceRoot(path = kotlin.sourceSets.getByName("iosMain").kotlin.srcDirs.first().toString())
        }
        val jsMain by creating {
            dependsOn(commonMain)
            sourceRoot(path = kotlin.sourceSets.getByName("jsMain").kotlin.srcDirs.first().toString())
        }
    }
}

apply(plugin = "com.vanniktech.maven.publish")
