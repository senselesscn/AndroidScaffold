@file:JvmName("IoUtils")

package cn.senseless.scaffold.utils

import android.net.Uri
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.InputStream

fun Uri.asRequestBody(contentType: MediaType? = null): RequestBody {
    return object : RequestBody() {
        override fun contentType(): MediaType? {
            return contentType
        }

        override fun writeTo(sink: BufferedSink) {
            openInputStream()?.run {
                source().use {
                    sink.writeAll(it)
                }
            }
        }
    }
}

fun Uri.openInputStream(): InputStream? {
    val contentResolver = ApplicationHolder.appContext.contentResolver
    return contentResolver.openInputStream(this)
        ?: contentResolver.openAssetFileDescriptor(this, "r")?.createInputStream()
}

fun Uri.readBytes(): ByteArray? {
    openInputStream()?.use {
        return it.readBytes()
    }
    return null
}