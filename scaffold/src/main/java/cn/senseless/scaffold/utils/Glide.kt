@file:JvmName("GlideUtils")

package cn.senseless.scaffold.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import cn.senseless.scaffold.GlideApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * glide封装
 *
 * source支持类型：Uri,String,File,URL,byte[],resourceId,Drawable,Bitmap;
 * InputStream，ByteBuffer，ParcelFileDescriptor，AssetFileDescriptor
 * @author lig
 * @date 2022-03-22
 */

fun ImageView.load(source: Any?) {
    if (source == null) return
    if (source is String && source.isEmpty()) return
    GlideApp.with(this)
        .load(source)
        .into(this)
}

/**
 * 从资源中加载图片
 */
fun ImageView.loadAssetImage(assetName: String) {
    GlideApp.with(this)
        .load("file:///android_asset/$assetName")
        .into(this)
}

/**
 * 加载bitmap，失败时返回null
 */
suspend fun loadBitmap(context: Context, source: Any): Bitmap? {
    return withContext(Dispatchers.IO) {
        kotlin.runCatching {
            GlideApp.with(context)
                .asBitmap()
                .load(source)
                .submit()
                .get()
        }.getOrNull()
    }
}

suspend fun loadAssetBitmap(context: Context, assetName: String, width: Int, height: Int): Bitmap? {
    return withContext(Dispatchers.IO) {
        kotlin.runCatching {
            GlideApp.with(context)
                .asBitmap()
                .load("file:///android_asset/$assetName")
                .override(width, height)
                .submit()
                .get()
        }.getOrNull()
    }
}