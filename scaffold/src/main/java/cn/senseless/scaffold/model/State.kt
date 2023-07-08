package cn.senseless.scaffold.model

import cn.senseless.scaffold.utils.ToastUtils

sealed class State<out T> {

    data class Loading(@JvmField val progress: Int) : State<Nothing>()

    data class Error(@JvmField val code: Int, @JvmField val message: String) : State<Nothing>() {

        fun toast() {
            if (code > 0) ToastUtils.toast(message)
        }
    }

    data class Success<T>(@JvmField val data: T) : State<T>()
}