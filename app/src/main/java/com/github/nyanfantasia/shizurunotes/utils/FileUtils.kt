package com.github.nyanfantasia.shizurunotes.utils

import com.blankj.utilcode.util.LogUtils
import com.github.nyanfantasia.shizurunotes.common.Statics
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object FileUtils {
    val dbDirectoryPath: String
        get() = Utils.app!!.dataDir.absolutePath + "/databases"
    val dbFilePath: String
        get() = Utils.app!!.getDatabasePath(Statics.DB_FILE_NAME).absolutePath
    val compressedDbFilePath: String
        get() = Utils.app!!.getDatabasePath(Statics.DB_FILE_NAME_COMPRESSED).absolutePath

    fun getFilePath(fileName: String): String {
        return Utils.app!!.filesDir.absolutePath + "/" + fileName
    }

    /***
     * Just copy file
     * @param srcPath Source File Path
     * @param desPath Destination File Path
     * @param delete  Delete Source File Prompt
     */
    fun copyFile(fileName: String, srcPath: String, desPath: String, delete: Boolean) {
        val srcFilePath = srcPath + fileName
        val desFilePath = desPath + fileName
        val dataBaseDir = File(desPath)
        //Check database folder existence
        if (!dataBaseDir.exists()) dataBaseDir.mkdirs()
        val exDB = File(desFilePath)
        if (exDB.exists()) exDB.delete()
        try {
            val fileInputStream = FileInputStream(srcFilePath)
            val fileOutputStream = FileOutputStream(exDB)
            val buffer = ByteArray(1024)
            var count: Int
            while (fileInputStream.read(buffer).also { count = it } > 0) fileOutputStream.write(
                buffer,
                0,
                count
            )
            fileOutputStream.flush()
            fileOutputStream.close()
            fileInputStream.close()
            if (delete) {
                val srcFile = File(srcFilePath)
                srcFile.delete()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun deleteDirectory(directoryFile: File): Boolean {
        if (directoryFile.isDirectory) {
            val files = directoryFile.listFiles()
            if (files != null) {
                for (child in files) {
                    deleteDirectory(child)
                }
            }
        }
        return deleteFile(directoryFile)
    }

    fun deleteFile(filePath: String): Boolean {
        return deleteFile(File(filePath))
    }

    fun deleteFile(file: File): Boolean {
        var flag = true
        try {
            if (!file.delete()) {
                flag = false
                throw IOException("Failed to delete file: " + file.absolutePath + ". Size: " + file.length() / 1024 + "KB.")
            } else {
                LogUtils.file("FileDelete", "Delete file " + file.absolutePath)
            }
        } catch (e: Exception) {
            LogUtils.file(LogUtils.E, "FileDelete", e.message)
        }
        return flag
    }

    fun checkFile(file: File): Boolean {
        if (!file.exists()) {
            LogUtils.file(LogUtils.I, "FileCheck", "FileNotExists: " + file.absolutePath)
            return false
        }
        return true
    }

    fun checkFile(filePath: String): Boolean {
        val file = File(filePath)
        return checkFile(file)
    }

    fun checkFileAndSize(filePath: String, border: Long): Boolean {
        val file = File(filePath)
        if (!checkFile(file)) {
            return false
        }
        if (file.length() < border * 1024) {
            LogUtils.file(
                LogUtils.W,
                "FileCheck",
                "AbnormalDbFileSize: " + file.length() / 1024 + "KB." + " At: " + file.absolutePath
            )
            return false
        }
        LogUtils.file(
            LogUtils.I,
            "FileCheck",
            file.absolutePath + ". Size: " + file.length() / 1024 + "KB."
        )
        return true
    }

    fun checkFileAndDeleteIfExists(file: File) {
        if (file.exists()) deleteFile(file)
    }

    /**
     * Return the MD5 of file.
     *
     * @param filePath The path of file.
     * @return the md5 of file
     */
    fun getFileMD5ToString(filePath: String): String {
        val file = File(filePath)
        return getFileMD5ToString(file)
    }

    /**
     * Return the MD5 of file.
     *
     * @param file The file.
     * @return the md5 of file
     */
    fun getFileMD5ToString(file: File?): String {
        return Utils.bytes2HexString(getFileMD5(file), true)
    }

    /**
     * Return the MD5 of file.
     *
     * @param filePath The path of file.
     * @return the md5 of file
     */
    fun getFileMD5(filePath: String): ByteArray? {
        return getFileMD5(File(filePath))
    }

    /**
     * Return the MD5 of file.
     *
     * @param file The file.
     * @return the md5 of file
     */
    fun getFileMD5(file: File?): ByteArray? {
        if (file == null) return null
        var dis: DigestInputStream? = null
        try {
            val fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            dis = DigestInputStream(fis, md)
            val buffer = ByteArray(1024 * 256)
            while (true) {
                if (dis.read(buffer) <= 0) break
            }
            md = dis.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                dis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}