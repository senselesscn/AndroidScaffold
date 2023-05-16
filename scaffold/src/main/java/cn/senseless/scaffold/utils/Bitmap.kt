@file:JvmName("BitmapUtils")

package cn.senseless.scaffold.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

fun Bitmap.readAllBytes(format: Bitmap.CompressFormat): ByteArray {
    val baos = ByteArrayOutputStream()
    compress(format, 100, baos)
    return baos.toByteArray()
}