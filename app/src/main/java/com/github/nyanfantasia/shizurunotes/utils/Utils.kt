package com.github.nyanfantasia.shizurunotes.utils

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

object Utils {
    var app: Application? = null

    /***
     * Divide by ","
     * @param list Array list to split
     * @return Split array list
     */
    fun splitIntegerWithComma(list: ArrayList<Int?>): String? {
        if (list.isEmpty()) return null
        val sb = StringBuilder()
        for (item in list) {
            sb.append(item).append(",")
        }
        sb.deleteCharAt(sb.lastIndexOf(","))
        return sb.toString()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Throws(IOException::class)
    fun saveBitmap(
        context: Context, bitmap: Bitmap, displayName: String
    ): Uri {

        val relativePath = Environment.DIRECTORY_PICTURES + File.separator + "ShizuruNotes"

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        }

        var uri: Uri? = null

        return runCatching {
            with(context.contentResolver) {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it // Keep uri reference so it can be removed on failure

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream))
                            throw IOException("Failed to save bitmap.")
                    } ?: throw IOException("Failed to open output stream.")

                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.getOrElse {
            uri?.let { orphanUri ->
                // Don't leave an orphan entry in the MediaStore
                context.contentResolver.delete(orphanUri, null, null)
            }

            throw it
        }
    }

    @JvmStatic
    fun getValueFromObject(`object`: Any, fieldName: String): Any? {
        val field: Field
        return try {
            field = `object`.javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field[`object`]
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    private val format = DecimalFormat("#")
    @JvmStatic
    fun roundDownDouble(value: Double): String {
        return format.format(floor(value))
    }

    fun roundUpDouble(value: Double): String {
        return format.format(ceil(value))
    }

    @JvmStatic
    fun roundDouble(value: Double): String {
        return format.format(round(value))
    }

    @JvmStatic
    fun roundIfNeed(value: Double): String {
        return if (value % 1 == 0.0) {
            roundDouble(value)
        } else {
            value.toString()
        }
    }

    private val format2 = DecimalFormat("0.0")
    fun getOneDecimalPlaces(value: Double?): String {
        return format2.format(value)
    }

    val currentProcessName: String
        get() = Thread.currentThread().name
    val screenRatio: Double
        get() {
            val metrics = app!!.resources.displayMetrics
            return metrics.heightPixels.toString().toDouble() / metrics.widthPixels
        }
    private val HEX_DIGITS_UPPER =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    private val HEX_DIGITS_LOWER =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * Bytes to hex string.
     *
     * e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }, true) returns "00A8"
     *
     * @param bytes       The bytes.
     * @param isUpperCase True to use upper case, false otherwise.
     * @return hex string
     */
    fun bytes2HexString(bytes: ByteArray?, isUpperCase: Boolean): String {
        if (bytes == null) return ""
        val hexDigits = if (isUpperCase) HEX_DIGITS_UPPER else HEX_DIGITS_LOWER
        val len = bytes.size
        if (len <= 0) return ""
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = hexDigits[bytes[i].toInt() shr 4 and 0x0f]
            ret[j++] = hexDigits[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(ret)
    }
}