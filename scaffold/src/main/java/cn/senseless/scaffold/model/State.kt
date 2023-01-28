package cn.senseless.scaffold.model

import cn.senseless.scaffold.utils.ToastUtils

sealed class State<out T> {

    data class Loading(val progress: Int) : State<Nothing>()

    data class Error(val code: Int, val message: String) : State<Nothing>() {

        fun toast() {
            if (code > 0) ToastUtils.toast(message)
        }
    }

    data class Success<T>(val data: T?) : State<T>() {

        @Throws(NullPointerException::class)
        fun requireData(): T {
            if (data == null) {
                throw NullPointerException()
            }
            return data
        }
    }
}