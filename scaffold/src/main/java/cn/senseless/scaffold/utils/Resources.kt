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
inline fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(this, id)!!

inline fun View.getDrawableCompat(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(context, id)!!

inline fun Fragment.getDrawableCompat(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(requireContext(), id)!!

/**
 * 输入id，返回与当前主题关联的Color
 */
inline fun Context.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

inline fun View.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

inline fun Fragment.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(requireContext(), id)