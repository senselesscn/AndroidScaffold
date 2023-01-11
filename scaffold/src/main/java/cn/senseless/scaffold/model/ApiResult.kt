package cn.senseless.scaffold.model

import com.google.gson.annotations.SerializedName

data class ApiResult<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: T?
) {
    val isSuccessful: Boolean
        get() = code == 200

    fun asState(): State<T> {
        return if (isSuccessful) {
            State.Success(result)
        } else {
            State.Error(code, message ?: "服务器异常")
        }
    }
}