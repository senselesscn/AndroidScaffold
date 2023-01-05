package cn.senseless.scaffold.utils

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process


object ApplicationHolder {
    private var application: Application? = null

    val appContext: Application
        get() = application ?: throw IllegalArgumentException("请先在Application的onCreate中初始化ContextHolder")

    fun init(application: Application) {
        if (isMainProcess(application)) {
            this.application = application
            ActivityManager.register(application)
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getMainProcessName(context: Context): String {
        return context.packageManager.getApplicationInfo(context.packageName, 0).processName
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun isMainProcess(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val pid = Process.myPid()
        val pName = getMainProcessName(context)
        for (process in am.runningAppProcesses) {
            if (process.pid == pid && process.processName == pName) {
                //进程ID相同时判断该进程名是否一致
                return true
            }
        }
        return false
    }
}