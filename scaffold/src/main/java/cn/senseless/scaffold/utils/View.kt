@file:JvmName("ViewUtils")

package cn.senseless.scaffold.utils

import android.annotation.SuppressLint
import android.graphics.Outline
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


fun View.onClick(action: (v: View) -> Unit) {
    onClick(400, action)
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
        override fun onDown(e: MotionEvent): Boolean {
            onDown(this@onMultiClick)
            return super.onDown(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick(this@onMultiClick)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            onClick(this@onMultiClick)
            return true
        }
    }
    val gestureDetector = GestureDetector(context, gestureListener)
    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
    }
}

fun View.disableTooltipText() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (this is ViewGroup) {
            forEach {
                it.disableTooltipText()
            }
        }
        TooltipCompat.setTooltipText(this, null)
    }
}

inline fun RecyclerView.disableAnimations() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}

/**
 * 使用此函数会清除已有的Decoration
 */
fun RecyclerView.setItemOffsets(block: (position: Int, itemCount: Int, outRect: Rect) -> Unit) {
    for (i in 0 until itemDecorationCount) {
        removeItemDecorationAt(i)
    }
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val adapterPosition = parent.getChildAdapterPosition(view)
            block(adapterPosition, adapter?.itemCount ?: 0, outRect)
        }
    })
}

fun View.disableOverScroller() {
    overScrollMode = View.OVER_SCROLL_NEVER
    if (this is ViewGroup) {
        forEach {
            it.disableOverScroller()
        }
    }
}

inline fun TabLayout.addOnTabSelectedListener(
    crossinline onTabSelected: (tab: TabLayout.Tab) -> Unit = {},
    crossinline onTabUnselected: (tab: TabLayout.Tab) -> Unit = {},
    crossinline onTabReselected: (tab: TabLayout.Tab) -> Unit = {},
): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            onTabSelected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            onTabUnselected(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            onTabReselected(tab)
        }
    }
    addOnTabSelectedListener(listener)
    return listener
}

fun View.clipRadius(radius: Float) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.clipRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            val path = Path()
            val radii = floatArrayOf(
                topLeft, topLeft, topRight, topRight,
                bottomRight, bottomRight, bottomLeft, bottomLeft
            )
            path.addRoundRect(0f, 0f, view.width.toFloat(), view.height.toFloat(), radii, Path.Direction.CW)
            outline.setPath(path)
        }
    }
}

fun View.clipOval() {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(0, 0, view.width, view.height)
        }
    }
}