package cn.senseless.scaffold.utils

import android.app.Application


object ApplicationHolder {
    private var application: Application? = null

    val appContext: Application
        get() = application
            ?: throw IllegalArgumentException("请先在Application的onCreate中初始化ContextHolder")

    fun init(application: Application) {
        this.application = application
        ActivityManager.register(application)
    }
}