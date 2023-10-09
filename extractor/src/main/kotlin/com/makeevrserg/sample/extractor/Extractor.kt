package com.makeevrserg.sample.extractor

import com.makeevrserg.sample.jvm.JvmExtractor
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import unzip
import java.io.File

sealed class Extractor(val name: String) {
    abstract fun extract(zipFile: File, zipOutputFolder: File)

    data object Java : Extractor("Java") {
        override fun extract(zipFile: File, zipOutputFolder: File) {
            val newOutputFolder = File(File(zipOutputFolder, "jvm"), zipFile.name)
            if (newOutputFolder.exists()) newOutputFolder.delete()
            if (!newOutputFolder.exists()) newOutputFolder.mkdirs()
            JvmExtractor.unzip(zipFile, newOutputFolder)
        }
    }

    data object Okio : Extractor("Okio") {
        override fun extract(zipFile: File, zipOutputFolder: File) {
            val newOutputFolder = File(File(zipOutputFolder, "okio"), zipFile.name)
            if (newOutputFolder.exists()) newOutputFolder.delete()
            if (!newOutputFolder.exists()) newOutputFolder.mkdirs()
            FileSystem.SYSTEM.unzip(zipFile.toOkioPath(), newOutputFolder.toOkioPath())
        }
    }
}
