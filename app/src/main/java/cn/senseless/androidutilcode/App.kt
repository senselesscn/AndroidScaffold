package cn.senseless.androidutilcode

import android.app.Application
import cn.senseless.scaffold.utils.ApplicationHolder

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationHolder.init(this)
    }
}