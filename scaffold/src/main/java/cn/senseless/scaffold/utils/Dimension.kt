@file:JvmName("DimensionUtils")

package cn.senseless.scaffold.utils

import android.content.res.Resources
import android.util.TypedValue

//输入dp输出px
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = toFloat().dp

//值转为int
val Float.idp
    get() = dp.toInt()

val Int.idp
    get() = dp.toInt()

//输入sp输出px
val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.sp
    get() = toFloat().sp
