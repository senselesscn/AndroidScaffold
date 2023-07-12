package cn.senseless.scaffold.utils

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2


inline fun ViewPager2.addOnPageChangeListener(
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

inline fun ViewPager.addOnPageChangeListener(
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