pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android" || requested.id.name == "kotlin-android-extensions") {
                useModule("com.android.tools.build:gradle:4.1.3")
            } else if (requested.id.namespace == "org.jetbrains.kotlin" || requested.id.namespace == "org.jetbrains.kotlin.native") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
            }
        }
    }
}
rootProject.name = "mmapp"

includeBuild("gradle-plugin")
include("library")