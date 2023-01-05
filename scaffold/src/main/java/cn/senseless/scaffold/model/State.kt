package cn.senseless.scaffold.model

sealed class State<out T> {
    data class Loading(val progress: Int) : State<Nothing>()
    data class Error(val code: Int, val message: String) : State<Nothing>()
    data class Success<T>(val data: T?) : State<T>()
}