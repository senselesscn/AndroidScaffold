package cn.senseless.scaffold.model

data class ApiResult<T>(
    val code: Int,
    val message: String?,
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