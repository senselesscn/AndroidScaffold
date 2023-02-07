package cn.senseless.scaffold.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * 直接画布切圆角的TextView，允许设置边框
 */
class RoundImageView : AppCompatImageView, RoundedCorners {
    private val roundHelper = RoundedCornersHelper(this)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        roundHelper.loadAttrs(context, attrs)
    }

    override fun draw(canvas: Canvas) {
        roundHelper.withClip(canvas) {
            super.draw(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        roundHelper.onSizeChange(w, h)
    }

    override fun topLeftRadius(r: Float) {
        roundHelper.topLeftRadius(r)
    }

    override fun topRightRadius(r: Float) {
        roundHelper.topRightRadius(r)
    }

    override fun bottomLeftRadius(r: Float) {
        roundHelper.bottomLeftRadius(r)
    }

    override fun bottomRightRadius(r: Float) {
        roundHelper.bottomRightRadius(r)
    }

    override fun leftRadius(r: Float) {
        roundHelper.leftRadius(r)
    }

    override fun rightRadius(r: Float) {
        roundHelper.rightRadius(r)
    }

    override fun topRadius(r: Float) {
        roundHelper.topRadius(r)
    }

    override fun bottomRadius(r: Float) {
        roundHelper.bottomRadius(r)
    }

    override fun borderWidth(width: Float) {
        roundHelper.borderWidth(width)
    }

    override fun borderColor(c: Int) {
        roundHelper.borderColor(c)
    }

    override fun radius(r: Float) {
        roundHelper.radius(r)
    }

    override fun clearRadius() {
        roundHelper.clearRadius()
    }
}