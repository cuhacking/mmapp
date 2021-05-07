buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.15.1")
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.4.32"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
