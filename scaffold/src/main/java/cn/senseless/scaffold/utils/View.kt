@file:JvmName("ViewUtils")

package cn.senseless.scaffold.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


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

fun RecyclerView.disableAnimations() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}

inline fun RecyclerView.setItemOffsets(crossinline block: (position: Int, outRect: Rect) -> Unit) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            block(parent.getChildAdapterPosition(view), outRect)
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

inline fun ViewPager2.setOnPageChangeCallback(
    crossinline onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {},
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {}
): ViewPager2.OnPageChangeCallback {
    val listener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onPageSelected(position)
        }
    }
    registerOnPageChangeCallback(listener)
    return listener
}

inline fun ViewPager.setOnPageChangeListener(
    crossinline onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {},
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {}
): ViewPager.OnPageChangeListener {
    val listener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged(state)
        }

    }
    addOnPageChangeListener(listener)
    return listener
}

inline fun TabLayout.setOnTabSelectedListener(
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

inline fun SeekBar.setOnSeekBarChangeListener(
    crossinline onProgressChanged: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit = { _, _, _ -> },
    crossinline onStartTrackingTouch: (seekBar: SeekBar) -> Unit = {},
    crossinline onStopTrackingTouch: (seekBar: SeekBar) -> Unit = {}
): SeekBar.OnSeekBarChangeListener {
    val listener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onProgressChanged(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            onStartTrackingTouch(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            onStopTrackingTouch(seekBar)
        }
    }
    setOnSeekBarChangeListener(listener)
    return listener
}

inline fun SeekBar.doOnProgressChanged(crossinline action: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit) =
    setOnSeekBarChangeListener(onProgressChanged = action)

inline fun SeekBar.doOnStartTrackingTouch(crossinline action: (seekBar: SeekBar) -> Unit) =
    setOnSeekBarChangeListener(onStartTrackingTouch = action)

inline fun SeekBar.doOnStopTrackingTouch(crossinline action: (seekBar: SeekBar) -> Unit) =
    setOnSeekBarChangeListener(onStopTrackingTouch = action)