package com.cuhacking.mmapp.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ConfigureNetrcTask : DefaultTask() {
    @Input
    lateinit var downloadKey: String

    @TaskAction
    fun apply() {
        val homeDir = File(System.getProperty("user.home"))
        val netrcFile = homeDir.resolve(".netrc")

        if (netrcFile.exists()) {
            val lines = netrcFile.readLines()

            val match = lines.find { it.contains("api.mapbox.com") }
            if (match != null) {
                return
            }
        }

        // machine api.mapbox.com
        // login mapbox
        // password <INSERT SECRET API TOKEN>
        val text =
            listOf("machine api.mapbox.com", "login mapbox", "password $downloadKey").joinToString(separator = "\n")
        netrcFile.appendText(text)
    }
}