package cn.senseless.scaffold.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.lang.reflect.Type

object JsonUtils {
    private const val TAG = "JsonUtils"
    private val gson = Gson()

    @JvmStatic
    fun toJson(`object`: Any): String {
        return gson.toJson(`object`)
    }

    @JvmStatic
    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    @JvmStatic
    fun <T> fromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun <T> fromJsonOrNull(json: String?, clazz: Class<T>): T? {
        if (json.isNullOrBlank()) return null
        try {
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            Log.w(TAG, "fromJsonOrNull: ", e)
        }
        return null
    }

    @JvmStatic
    fun toJsonObj(input: String?): JSONObject? {
        if (input.isNullOrBlank()) return null
        return try {
            JSONObject(input)
        } catch (e: Exception) {
            Log.w(TAG, "toJsonObj: ", e)
            null
        }
    }
}