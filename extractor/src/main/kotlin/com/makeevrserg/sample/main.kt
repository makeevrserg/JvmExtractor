@file:Suppress("Filename")
@file:JvmName("Main")

package com.makeevrserg.sample

import com.makeevrserg.sample.extractor.Extractor
import java.io.File

const val ZIP_FILE_PATH = "./root/test.zip"
const val ZIP_OUTPUT_PATH = "./root"

fun main() {
    val zipFile = File(ZIP_FILE_PATH)
    val zipOutputFolder = File(ZIP_OUTPUT_PATH)
    if (!zipFile.exists()) {
        println("File ${zipFile.absolutePath} not found!")
        return
    }
    println("Started extraction of ${zipFile.absolutePath}")
    println("Extracting into ${zipOutputFolder.absolutePath}")
    val extractors = listOf(
        Extractor.Java,
        Extractor.Okio
    )
    extractors.forEach { extractor ->
        val result = runCatching {
            extractor.extract(zipFile, zipOutputFolder)
        }
        result.onFailure { throwable ->
            println("Could not finish extraction of ${extractor.name}")
            throwable.printStackTrace()
        }
        result.onSuccess {
            println("Extractor '${extractor.name}' extracted files successfully")
        }
    }
}
