package cn.senseless.scaffold.net

import cn.senseless.scaffold.utils.JsonUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class Http(private val url: String) {

    @Throws(Exception::class)
    suspend inline fun <reified T> get(headers: Map<String, String>? = null): T {
        return get(T::class.java, headers)
    }

    @Throws(Exception::class)
    suspend inline fun <reified T> post(
        headers: Map<String, String>? = null,
        body: RequestBody? = null
    ): T {
        return post(T::class.java, headers, body)
    }

    @Throws(Exception::class)
    suspend fun download(headers: Map<String, String>? = null, file: File) {
        val fileOutputStream = file.outputStream()
        withContext(Dispatchers.IO) {
            get(InputStream::class.java, headers).use {
                it.copyTo(fileOutputStream)
                fileOutputStream.close()
            }
        }
    }

    @Throws(Exception::class)
    suspend fun <T> get(
        clazz: Class<T>,
        headers: Map<String, String>? = null,
    ): T {
        val request = Request.Builder().apply {
            url(url)
            method("GET", null)
            headers?.forEach { entry ->
                addHeader(entry.key, entry.value)
            }
        }.build()
        val call = okHttpClient.newCall(request)
        return execCall(call, clazz)
    }

    @Throws(Exception::class)
    suspend fun <T> post(
        clazz: Class<T>,
        headers: Map<String, String>? = null,
        body: RequestBody? = null
    ): T {
        val request = Request.Builder().apply {
            url(url)
            method("POST", body)
            headers?.forEach { entry ->
                addHeader(entry.key, entry.value)
            }
        }.build()
        val call = okHttpClient.newCall(request)
        return execCall(call, clazz)
    }

    @Throws(Exception::class)
    private suspend fun <T> execCall(call: Call, clazz: Class<T>) = suspendCancellableCoroutine {
        it.invokeOnCancellation {
            call.cancel()
        }
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val t = parseResponse(clazz, response)
                    it.resume(t)
                } catch (e: Exception) {
                    it.resumeWithException(e)
                }
            }
        })
    }

    @Throws(Exception::class)
    private fun <T> parseResponse(clazz: Class<T>, response: Response): T {
        val responseBody = response.body ?: throw IOException("responseBody is null")
        if (clazz == InputStream::class.java) {
            return responseBody.byteStream() as T
        }
        if (clazz == String::class.java || clazz == CharSequence::class.java) {
            return responseBody.string() as T
        }
        return JsonUtils.fromJson(responseBody.string(), clazz)
    }

    companion object {
        private val okHttpClient: OkHttpClient = OkHttpClient()
    }
}

fun Map<String, Any>.form(): RequestBody {
    val builder = FormBody.Builder()
    forEach {
        builder.add(it.key, it.value.toString())
    }
    return builder.build()
}

fun Map<String, Any>.json(): RequestBody {
    return JsonUtils.toJson(this).toRequestBody("application/json".toMediaTypeOrNull())
}

fun http(url: String): Http {
    return Http(url)
}