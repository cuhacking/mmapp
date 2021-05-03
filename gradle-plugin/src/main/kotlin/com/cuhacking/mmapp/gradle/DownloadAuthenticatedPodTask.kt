package com.cuhacking.mmapp.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import java.io.File

class DownloadAuthenticatedPodTask : DefaultTask() {

    @get:Input
    var location: String? = null

    @get:Input
    var username: String? = null

    @get:Input
    var password: String? = null

    /*@get:OutputDirectory
    val outputFile = File()*/

    private fun downloadFile() {

    }
}