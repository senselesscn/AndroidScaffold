package cn.senseless.scaffold.utils

import android.graphics.drawable.Drawable
import android.widget.TextView

inline fun TextView.clearCompoundDrawables() {
    setCompoundDrawables(null, null, null, null)
}

inline fun TextView.setLeftCompoundDrawable(drawable: Drawable?) {
    setCompoundDrawables(drawable, null, null, null)
}

inline fun TextView.setTopCompoundDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, drawable, null, null)
}

inline fun TextView.setRightCompoundDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, null, drawable, null)
}

inline fun TextView.setBottomCompoundDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, null, null, drawable)
}
