package cn.senseless.scaffold.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.senseless.scaffold.model.ApiResult
import cn.senseless.scaffold.model.State
import cn.senseless.scaffold.utils.ToastUtils
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)

    fun <T> request(block: suspend () -> ApiResult<T>): LiveData<State<T>> {
        val liveData = MutableLiveData<State<T>>()
        launch {
            liveData.postValue(State.Loading(0))
            val state = getState(block)
            if (state is State.Error) {
                ToastUtils.toast(state.message)
            }
            liveData.postValue(state)
        }
        return liveData
    }

    private suspend fun <T> getState(block: suspend () -> ApiResult<T>): State<T> {
        try {
            val apiResult = block()
            return apiResult.asState()
        } catch (e: Exception) {
            Log.w(TAG, "getState: ", e)
            return when (e) {
                is CancellationException -> {
                    State.Error(0, "")
                }
                is HttpException -> {
                    State.Error(500, "服务器异常")
                }
                is SocketTimeoutException -> {
                    State.Error(400, "网络连接超时，请重试")
                }
                is UnknownHostException -> {
                    State.Error(400, "网络连接失败，请检查网络设置")
                }
                else -> {
                    State.Error(600, "未知错误")
                }
            }
        }
    }

    companion object {
        private const val TAG = "BaseViewModel"
    }
}