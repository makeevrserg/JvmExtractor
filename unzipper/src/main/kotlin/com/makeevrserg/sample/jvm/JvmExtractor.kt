package com.makeevrserg.sample.jvm

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object JvmExtractor {
    @Throws(IOException::class)
    private fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
        val destFile = File(destinationDir, zipEntry.name)
        val destDirPath = destinationDir.getCanonicalPath()
        val destFilePath = destFile.getCanonicalPath()
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of the target dir: " + zipEntry.name)
        }
        return destFile
    }

    fun unzip(zipFile: File, zipOutputFolder: File) {
        val buffer = ByteArray(1024)
        val zis = ZipInputStream(zipFile.inputStream())
        var zipEntry = zis.getNextEntry()
        while (zipEntry != null) {
            val newFile: File = newFile(zipOutputFolder, zipEntry)
            if (zipEntry.isDirectory) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw IOException("Failed to create directory $newFile")
                }
            } else {
                // fix for Windows-created archives
                val parent = newFile.getParentFile()
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw IOException("Failed to create directory $parent")
                }

                // write file content
                val fos = FileOutputStream(newFile)
                var len: Int
                while (zis.read(buffer).also { len = it } > 0) {
                    fos.write(buffer, 0, len)
                }
                fos.close()
            }
            zipEntry = zis.getNextEntry()
        }

        zis.closeEntry()
        zis.close()
    }
}
