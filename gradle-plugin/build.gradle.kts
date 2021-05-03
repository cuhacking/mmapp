import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.4.32"
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("mmapp") {
            id = "com.cuhacking.mmapp"
            implementationClass = "com.cuhacking.mmapp.gradle.MmappPlugin"
        }
    }
}

group = "com.cuhacking.mmapp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    implementation("de.undercouch:gradle-download-task:4.1.1")
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
