@file:JvmName("ViewUtils")
package cn.senseless.scaffold.utils

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


fun View.onClick(action: (v: View) -> Unit) {
    setOnClickListener(action)
}

fun View.onClick(gap: Long, action: (v: View) -> Unit) {
    val onClickListener = object : View.OnClickListener {
        var lastClickElapsedRealtime = 0L

        override fun onClick(v: View) {
            val elapsedRealtime = SystemClock.elapsedRealtime()
            if (elapsedRealtime - lastClickElapsedRealtime >= gap) {
                lastClickElapsedRealtime = elapsedRealtime
                action.invoke(v)
            }
        }
    }
    setOnClickListener(onClickListener)
}

@SuppressLint("ClickableViewAccessibility")
fun View.onMultiClick(
    onDown: (v: View) -> Unit = {},
    onClick: (v: View) -> Unit = {},
    onDoubleClick: (v: View) -> Unit = {}
) {
    isClickable = true
    val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            onDown(this@onMultiClick)
            return super.onDown(e)
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            onDoubleClick(this@onMultiClick)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            onClick(this@onMultiClick)
            return true
        }
    }
    val gestureDetector = GestureDetector(context, gestureListener)
    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
    }
}