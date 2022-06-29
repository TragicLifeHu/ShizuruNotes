package com.github.nyanfantasia.shizurunotes.utils

import kotlin.Throws
import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream
import java.io.*

object BrotliUtils {
    /** Buffer byte  */
    private const val BUFFER = 1024

    /** Postfix name  */
    private const val EXT = ".br"

    /**
     * deCompress data
     * @param data Compressed data
     * @return  deCompressed data
     * @throws IOException Exception
     */
    @Throws(IOException::class)
    fun deCompress(data: ByteArray?): ByteArray? {
        val dataOutput: ByteArray?
        val byteArrayInputStream = ByteArrayInputStream(data)
        val byteArrayOutputStream = ByteArrayOutputStream()

        // deCompress
        deCompress(byteArrayInputStream, byteArrayOutputStream)
        dataOutput = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.flush()
        byteArrayOutputStream.close()
        byteArrayInputStream.close()
        return dataOutput
    }

    /**
     * File deCompress
     * @param file Compressed file
     * @param delete Delete source file
     * @throws IOException Exception
     */
    @Throws(IOException::class)
    fun deCompress(file: File, delete: Boolean) {
        val fis = FileInputStream(file)
        val fos = FileOutputStream(file.path.replace(EXT, ""))
        deCompress(fis, fos)
        fos.flush()
        fos.close()
        fis.close()
        if (delete) {
            file.delete()
        }
    }

    /**
     * deCompress
     * @param is Input Stream
     * @param os Output Stream
     * @throws IOException Exception
     */
    @Throws(IOException::class)
    private fun deCompress(`is`: InputStream, os: OutputStream) {
        val brotliCompressorInputStream = BrotliCompressorInputStream(`is`)
        var count: Int
        val data = ByteArray(BUFFER)
        while (brotliCompressorInputStream.read(data, 0, BUFFER).also { count = it } != -1) {
            os.write(data, 0, count)
        }
        brotliCompressorInputStream.close()
    }

    /**
     * File deCompress
     * @param path File path
     * @param delete Delete source file
     * @throws IOException Exception
     */
    @Throws(IOException::class)
    fun deCompress(path: String, delete: Boolean) {
        val file = File(path)
        deCompress(file, delete)
    }
}