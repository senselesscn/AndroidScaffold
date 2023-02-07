package cn.senseless.scaffold.utils

import androidx.lifecycle.liveData
import cn.senseless.scaffold.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.closeQuietly
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Proxy

object DownloadUtils {
    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .proxy(Proxy.NO_PROXY)
            .build()
    }

    /**
     * 禁止设置 HttpLoggingInterceptor.Level.BODY
     */
    fun setLogLevel(level: HttpLoggingInterceptor.Level) {
        httpLoggingInterceptor.level = level
    }

    /**
     * 异步下载，带进度
     */
    fun asyncDownload(url: String, file: File) = liveData<State<File>>(Dispatchers.IO) {
        emit(State.Loading(0))
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Connection", "close")
            .build()
        var outputStream: OutputStream? = null
        var inputStream: InputStream? = null
        try {
            okHttpClient.newCall(request).execute().body?.use {
                var progress = 0
                val contentLength = it.contentLength()
                inputStream = it.byteStream()
                outputStream = file.outputStream()
                var bytesCopied: Long = 0
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var bytes = inputStream!!.read(buffer)
                while (bytes >= 0) {
                    currentCoroutineContext().ensureActive()
                    outputStream!!.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    if (contentLength > 0) {
                        val currentProgress = bytesCopied.toFloat().div(contentLength).times(100).toInt()
                        if (currentProgress > progress) {
                            progress = currentProgress
                            emit(State.Loading(currentProgress))
                        }
                    }
                    bytes = inputStream!!.read(buffer)
                }
                emit(State.Success(file))
            } ?: throw IOException("content body is null")
        } catch (e: Exception) {
            emit(State.Error(0, "下载失败：${e.message}"))
        } finally {
            inputStream?.closeQuietly()
            outputStream?.closeQuietly()
        }
    }

    /**
     * 同步下载
     */
    @Throws(IOException::class)
    fun syncDownload(url: String, file: File) {
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("Connection", "close")
            .build()
        var outputStream: OutputStream? = null
        var inputStream: InputStream? = null
        try {
            okHttpClient.newCall(request).execute().body?.use {
                inputStream = it.byteStream()
                outputStream = file.outputStream()
                var bytesCopied: Long = 0
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var bytes = inputStream!!.read(buffer)
                while (bytes >= 0) {
                    outputStream!!.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    bytes = inputStream!!.read(buffer)
                }
            } ?: throw IOException("content body is null")
        } finally {
            inputStream?.closeQuietly()
            outputStream?.closeQuietly()
        }
    }
}