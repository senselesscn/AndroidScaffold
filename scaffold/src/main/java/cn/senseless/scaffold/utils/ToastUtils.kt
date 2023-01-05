package cn.senseless.scaffold.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast

object ToastUtils {
    private val handler = Handler(Looper.getMainLooper())

    @JvmStatic
    fun toast(msg: String?) {
        toast(msg, Toast.LENGTH_SHORT)
    }

    @JvmStatic
    fun toast(msg: String?, duration: Int) {
        if (msg.isNullOrEmpty()) {
            return
        }
        handler.post {
            val toast = Toast.makeText(ApplicationHolder.appContext, "", duration)
            toast.setText(msg)
            toast.show()
        }
    }

    @JvmStatic
    fun longToast(msg: String?) {
        toast(msg, Toast.LENGTH_LONG)
    }
}