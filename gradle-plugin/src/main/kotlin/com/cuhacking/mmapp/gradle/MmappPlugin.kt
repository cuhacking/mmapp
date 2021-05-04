package com.cuhacking.mmapp.gradle

import de.undercouch.gradle.tasks.download.Download
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Copy
import org.gradle.authentication.http.BasicAuthentication
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension
import java.io.File
import java.net.URI
import java.util.*

abstract class MmappPlugin : Plugin<Project> {
    private lateinit var extension: MmappExtension

    override fun apply(target: Project) {
        extension = target.extensions.create("mmapp", MmappExtension::class.java)
        val localProperties = target.rootDir.resolve("local.properties")
        val properties = Properties().apply { load(localProperties.inputStream()) }

        check(properties.containsKey("mapbox.download.key")) {
            "$PROP_MAPBOX_KEY not specified in ${localProperties.absolutePath}"
        }

        target.afterEvaluate {
            target.setupRepositories(properties)
            if (extension.applyDependencies) {
                target.setupDependencies(properties)
            }
        }
    }

    private fun Project.setupRepositories(props: Properties) {
        repositories.maven { repo ->
            with(repo) {
                url = URI("https://api.mapbox.com/downloads/v2/releases/maven")
                authentication { auth -> auth.create("basic", BasicAuthentication::class.java) }
                credentials { credentials ->
                    credentials.username = "mapbox"
                    credentials.password = props.getProperty(PROP_MAPBOX_KEY)
                }
            }
        }
    }

    private fun Project.setupDependencies(props: Properties) {
        check(plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
            "MMAPP Gradle plugin applied in '${project.path}' but Kotlin Multiplatform Plugin was not found"
        }

        val kotlinExtension = project.extensions.getByType(KotlinMultiplatformExtension::class.java)
        val sourceSets = kotlinExtension.sourceSets

        // Configure JS dependency
        try {
            sourceSets.getByName("jsMain").apply {
                dependencies {
                    implementation(npm("mapbox-gl", extension.jsVersion))
                }
            }
        } catch (_: UnknownDomainObjectException) {
            logger.debug("No js sourceSet named 'jsMain', skipping.")
        }

        // Configure Android SDK dependency
        try {
            sourceSets.getByName("androidMain").apply {
                dependencies {
                    implementation("com.mapbox.mapboxsdk:mapbox-android-sdk:${extension.androidSdkVersion}")
                }
            }
        } catch (_: UnknownDomainObjectException) {
            logger.debug("No android sourceSet named 'androidMain', skipping.")
        }

        // Configure iOS SDK dependency
        try {
            val podZip = buildDir.resolve("cocoapods/downloaded/mapbox-ios-sdk-dynamic.zip")
            val podDir = buildDir.resolve("cocoapods/downloaded/mapbox-ios-sdk-dynamic")
            tasks.register("downloadMapboxPod", Download::class.java) { task ->
                task.apply {
                    src("https://api.mapbox.com/downloads/v2/mobile-maps/releases/ios/packages/6.3.0/mapbox-ios-sdk-dynamic.zip")
                    username("mapbox")
                    password(props.getProperty(PROP_MAPBOX_KEY))
                    authScheme("Basic")
                    overwrite(false)
                    onlyIfModified(true)

                    dest(podZip)
                }
            }

            tasks.register("unzipMapboxPod", Copy::class.java) { task ->
                task.from(zipTree(podZip))
                task.into(podDir)
                task.dependsOn("downloadMapboxPod")
            }

            (kotlinExtension as ExtensionAware).extensions.getByType(CocoapodsExtension::class.java).apply {
                File("").toURI()
                pod("Mapbox-iOS-SDK") {
                    source = path(podDir)
                    moduleName = "Mapbox"
                }
            }

            tasks.getByName("generateDefMapbox").dependsOn("downloadMapboxPod")
        } catch (_: UnknownDomainObjectException) {
            logger.debug("No cocoapods to configure, skipping.")
        }
    }

    companion object {
        private const val PROP_MAPBOX_KEY = "mapbox.download.key"
    }
}