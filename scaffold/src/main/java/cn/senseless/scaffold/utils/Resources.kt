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
inline fun Context.compatDrawable(@DrawableRes id: Int): Drawable {
    return ContextCompat.getDrawable(this, id) ?: throw NotFoundException("Drawable资源不存在")
}

inline fun View.compatDrawable(@DrawableRes id: Int): Drawable {
    return context.compatDrawable(id)
}

@Throws(IllegalStateException::class)
inline fun Fragment.compatDrawable(@DrawableRes id: Int): Drawable {
    return requireContext().compatDrawable(id)
}

/**
 * 输入id，返回与当前主题关联的Color
 */
inline fun Context.compatColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

inline fun View.compatColor(@ColorRes id: Int): Int {
    return context.compatColor(id)
}

inline fun Fragment.compatColor(@ColorRes id: Int): Int {
    return requireContext().compatColor(id)
}