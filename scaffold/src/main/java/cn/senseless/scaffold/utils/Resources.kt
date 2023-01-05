package cn.senseless.scaffold.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * 输入id，返回与当前主题关联的Drawable
 */
@Throws(NotFoundException::class)
fun Context.compatDrawable(@DrawableRes id: Int): Drawable {
    return ContextCompat.getDrawable(this, id) ?: throw NotFoundException("Drawable资源不存在")
}

fun View.compatDrawable(@DrawableRes id: Int): Drawable {
    return context.compatDrawable(id)
}

@Throws(IllegalStateException::class)
fun Fragment.compatDrawable(@DrawableRes id: Int): Drawable {
    return requireContext().compatDrawable(id)
}

/**
 * 输入id，返回与当前主题关联的Color
 */
fun Context.compatColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun View.compatColor(@ColorRes id: Int): Int {
    return context.compatColor(id)
}

@Throws(IllegalStateException::class)
fun Fragment.compatColor(@ColorRes id: Int): Int {
    return requireContext().compatColor(id)
}