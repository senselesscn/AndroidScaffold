@file:JvmName("ContextUtils")
@file:Suppress("UNCHECKED_CAST")

package cn.senseless.scaffold.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun FragmentActivity.launch(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(context, start, block)

fun Fragment.launch(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewLifecycleOwner.lifecycleScope.launch(context, start, block)

fun LifecycleService.launch(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(context, start, block)

fun <T> Activity.intentValue(key: String) = lazy {
    intent.extras?.get(key) as? T
}

fun <T> Fragment.argumentsValue(key: String) = lazy {
    arguments?.get(key) as? T
}

/**
 * 获取元数据，获取不到返回null
 */
fun Context.getMetaData(key: String): String? {
    val result = kotlin.runCatching {
        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        info.metaData[key]?.toString()
    }
    return result.getOrNull()
}

/**
 * 获取app版本
 */
val Context.appVersionName: String
    get() {
        return packageManager.getPackageInfo(packageName, 0).versionName
    }

inline fun <reified T : Activity> Context.startActivity(vararg extras: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundleOf(*extras))
    startActivity(intent)
}

inline fun <reified T : Activity> Fragment.startActivity(vararg extras: Pair<String, Any?>) {
    requireContext().startActivity<T>(*extras)
}

inline fun <reified T : Service> Context.startService(vararg extras: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundleOf(*extras))
    startService(intent)
}