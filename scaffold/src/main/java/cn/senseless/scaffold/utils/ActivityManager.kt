package cn.senseless.scaffold.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

object ActivityManager : Application.ActivityLifecycleCallbacks {
    private val activities = LinkedList<Activity>()

    fun register(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    //栈顶
    fun topActivity(): Activity? {
        return activities.peekLast()
    }

    operator fun get(clazz: Class<Activity>): Activity? {
        return activities.find { it::class.java == clazz }
    }

    fun finishAll() {
        activities.forEach {
            it.finish()
        }
    }

    fun finishAllExcept(except: (Activity) -> Boolean) {
        activities.forEach {
            if (!except(it)) {
                it.finish()
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }
}