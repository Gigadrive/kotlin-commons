package com.gigadrivegroup.kotlincommons.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Arrays

// http://www.java2s.com/Tutorial/Java/0180__File/Utilityclassforsynchronizingfilesdirectories.htm

// http://www.java2s.com/Tutorial/Java/0180__File/Utilityclassforsynchronizingfilesdirectories.htm
/**
 * Utility class for synchronizing files/directories.
 *
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 * @author Hardy Ferentschik
 */
public object FileHelper {
    private const val DEFAULT_COPY_BUFFER_SIZE: Long =
        (16 * 1024 * 1024 // 16 MB
            ).toLong()

    private const val FAT_PRECISION = 2000

    @Throws(IOException::class)
    public fun areInSync(source: File, destination: File): Boolean {
        if (source.isDirectory) {
            if (!destination.exists()) {
                return false
            } else if (!destination.isDirectory) {
                throw IOException(
                    "Source and Destination not of the same type:" +
                        source.canonicalPath +
                        " , " +
                        destination.canonicalPath)
            }
            val sources = source.list()
            val srcNames: Set<String> = HashSet(listOf(*sources))
            val dests = destination.list()

            // check for files in destination and not in source
            for (fileName: String in dests) {
                if (!srcNames.contains(fileName)) {
                    return false
                }
            }
            var inSync = true
            for (fileName: String? in sources) {
                val srcFile = File(source, fileName)
                val destFile = File(destination, fileName)
                if (!areInSync(srcFile, destFile)) {
                    inSync = false
                    break
                }
            }
            return inSync
        } else {
            if (destination.exists() && destination.isFile) {
                val sts = source.lastModified() / FAT_PRECISION
                val dts = destination.lastModified() / FAT_PRECISION
                return sts == dts
            } else {
                return false
            }
        }
    }

    @JvmOverloads
    @Throws(IOException::class)
    public fun synchronize(
        source: File,
        destination: File,
        smart: Boolean,
        chunkSize: Long = DEFAULT_COPY_BUFFER_SIZE
    ) {
        var chunkSize = chunkSize
        if (chunkSize <= 0) {
            println("Chunk size must be positive: using default value.")
            chunkSize = DEFAULT_COPY_BUFFER_SIZE
        }
        if (source.isDirectory) {
            if (!destination.exists()) {
                if (!destination.mkdirs()) {
                    throw IOException("Could not create path $destination")
                }
            } else if (!destination.isDirectory) {
                throw IOException(
                    ("Source and Destination not of the same type:" +
                        source.canonicalPath +
                        " , " +
                        destination.canonicalPath))
            }
            val sources = source.list()
            val srcNames: Set<String> = HashSet(Arrays.asList(*sources))
            val dests = destination.list()

            // delete files not present in source
            for (fileName: String in dests) {
                if (!srcNames.contains(fileName)) {
                    delete(File(destination, fileName))
                }
            }
            // copy each file from source
            for (fileName: String? in sources) {
                val srcFile = File(source, fileName)
                val destFile = File(destination, fileName)
                synchronize(srcFile, destFile, smart, chunkSize)
            }
        } else {
            if (destination.exists() && destination.isDirectory) {
                delete(destination)
            }
            if (destination.exists()) {
                val sts = source.lastModified() / FAT_PRECISION
                val dts = destination.lastModified() / FAT_PRECISION
                // do not copy if smart and same timestamp and same length
                if (!smart ||
                    (sts == 0L) ||
                    (sts != dts) ||
                    (source.length() != destination.length())) {
                    copyFile(source, destination, chunkSize)
                }
            } else {
                copyFile(source, destination, chunkSize)
            }
        }
    }

    @Throws(IOException::class)
    private fun copyFile(srcFile: File, destFile: File, chunkSize: Long) {
        var `is`: FileInputStream? = null
        var os: FileOutputStream? = null
        try {
            `is` = FileInputStream(srcFile)
            val iChannel = `is`.getChannel()
            os = FileOutputStream(destFile, false)
            val oChannel = os.getChannel()
            var doneBytes = 0L
            var todoBytes = srcFile.length()
            while (todoBytes != 0L) {
                val iterationBytes = Math.min(todoBytes, chunkSize)
                val transferredLength = oChannel.transferFrom(iChannel, doneBytes, iterationBytes)
                if (iterationBytes != transferredLength) {
                    throw IOException(
                        ("Error during file transfer: expected " +
                            iterationBytes +
                            " bytes, only " +
                            transferredLength +
                            " bytes copied."))
                }
                doneBytes += transferredLength
                todoBytes -= transferredLength
            }
        } finally {
            `is`?.close()
            os?.close()
        }
        val successTimestampOp = destFile.setLastModified(srcFile.lastModified())
        if (!successTimestampOp) {
            println(
                "Could not change timestamp for {}. Index synchronization may be slow. $destFile")
        }
    }

    private fun delete(file: File) {
        if (file.isDirectory) {
            for (subFile: File in file.listFiles()) {
                delete(subFile)
            }
        }
        if (file.exists()) {
            if (!file.delete()) {
                println("Could not delete {}$file")
            }
        }
    }
}
