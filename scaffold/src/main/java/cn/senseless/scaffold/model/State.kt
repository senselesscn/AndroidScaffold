package cn.senseless.scaffold.model

import cn.senseless.scaffold.utils.ToastUtils
import kotlin.random.Random

sealed class State<out T> {

    open class Loading(@JvmField val progress: Int) : State<Nothing>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Loading

            if (progress != other.progress) return false

            return true
        }

        override fun hashCode(): Int {
            Random.Default
            return progress
        }

        override fun toString(): String {
            return "Loading(progress=$progress)"
        }

        object Default : Loading(0)
    }

    open class Error(@JvmField val code: Int, @JvmField val message: String) : State<Nothing>() {

        fun toast() {
            if (code > 0) ToastUtils.toast(message)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Error

            if (code != other.code) return false
            if (message != other.message) return false

            return true
        }

        override fun hashCode(): Int {
            var result = code
            result = 31 * result + message.hashCode()
            return result
        }

        override fun toString(): String {
            return "Error(code=$code, message='$message')"
        }

        object Default : Error(0, "")
    }

    class Success<T>(@JvmField val data: T) : State<T>() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success<*>

            if (data != other.data) return false

            return true
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }

        override fun toString(): String {
            return "Success(data=$data)"
        }
    }
}