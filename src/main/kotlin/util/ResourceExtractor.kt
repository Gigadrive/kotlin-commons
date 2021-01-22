package com.gigadrivegroup.kotlincommons.util

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.jar.JarFile

public class ResourceExtractor
/**
 * You can extract complete folders of resources from your plugin jar to a target folder. You can
 * append a regex to match the file names.
 *
 * @param jarFile The jar file of this application.
 * @param extractfolder The folder where the files will be extracted to.
 * @param folderpath The path where the files are inside in the jar located.
 * @param regex A regex to match the file names. This can be 'null' if you don't want to use it.
 */
(
    protected val jarFile: File,
    protected val extractfolder: File,
    protected val folderpath: String,
    protected val regex: String?
) {
    /**
     * Starts extracting the files.
     *
     * @param override Whether you want to override the old files.
     * @param subpaths Whether you want to create sub folders if it's also found in the jar file.
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    public fun extract(override: Boolean = false, subpaths: Boolean = true) {
        /** Make the folders if missing. */
        if (!extractfolder.exists()) {
            extractfolder.mkdirs()
        }
        val jar = JarFile(jarFile)

        /** Loop through all the entries. */
        val entries = jar.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            val path = entry.name
            /** Not in the folder. */
            if (!path.startsWith(folderpath)) {
                continue
            }
            if (entry.isDirectory) {
                if (subpaths) {
                    val file =
                        File(extractfolder, entry.name.replaceFirst(folderpath.toRegex(), ""))
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                }
            } else {
                var file: File
                /** Use the right path. */
                file =
                    if (subpaths) {
                        File(extractfolder, path.replaceFirst(folderpath.toRegex(), ""))
                    } else {
                        File(extractfolder, path.substring(path.indexOf(File.separatorChar)))
                    }
                val name = file.name
                /** Be sure that the file is valid. */
                if (regex == null || name.matches(Regex(regex))) {
                    if (file.exists() && override) {
                        file.delete()
                    }
                    if (!file.exists()) {
                        /** Copy the file to the path. */
                        val `is`: InputStream = jar.getInputStream(entry)
                        val fos = FileOutputStream(file)
                        while (`is`.available() > 0) {
                            fos.write(`is`.read())
                        }
                        fos.close()
                        `is`.close()
                    }
                }
            }
        }
        jar.close()
    }
}
