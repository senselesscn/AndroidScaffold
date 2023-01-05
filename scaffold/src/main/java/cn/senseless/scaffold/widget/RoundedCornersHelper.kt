package cn.senseless.scaffold.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.senseless.scaffold.R

class RoundedCornersHelper(private val host: View) : RoundedCorners {
    private val path: Path = Path()
    private val paint: Paint = Paint()
    private val bounds: RectF = RectF()
    private var topLeftRadius = 0f
    private var topRightRadius = 0f
    private var bottomLeftRadius = 0f
    private var bottomRightRadius = 0f
    private var borderColor = 0
    private var borderWidth = 0f
    private var hasLoadAttrs = false

    fun onSizeChange(w: Int, h: Int) {
        bounds.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    fun loadAttrs(context: Context, attrs: AttributeSet?) {
        paint.isAntiAlias = true
        hasLoadAttrs = false
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundView)
        topLeftRadius(a.getDimension(R.styleable.RoundView_topLeftRadius, 0f))
        topRightRadius(a.getDimension(R.styleable.RoundView_topRightRadius, 0f))
        bottomLeftRadius(a.getDimension(R.styleable.RoundView_bottomLeftRadius, 0f))
        bottomRightRadius(a.getDimension(R.styleable.RoundView_bottomRightRadius, 0f))
        bottomRadius(a.getDimension(R.styleable.RoundView_bottomRadius, 0f))
        topRadius(a.getDimension(R.styleable.RoundView_topRadius, 0f))
        leftRadius(a.getDimension(R.styleable.RoundView_leftRadius, 0f))
        rightRadius(a.getDimension(R.styleable.RoundView_rightRadius, 0f))
        radius(a.getDimension(R.styleable.RoundView_radius, 0f))
        borderColor(a.getColor(R.styleable.RoundView_borderColor, 0))
        borderWidth(a.getDimension(R.styleable.RoundView_borderWidth, 0f))
        a.recycle()
        hasLoadAttrs = true
    }

    fun withClip(canvas: Canvas, callSuper: () -> Unit) {
        if (topLeftRadius == 0f && topRightRadius == 0f && bottomLeftRadius == 0f && bottomRightRadius == 0f) {
            callSuper()
            return
        }
        if (canvas.drawFilter == null) {
            canvas.drawFilter = PaintFlagsDrawFilter(
                0,
                Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
            )
        }
        path.reset()
        val radii = floatArrayOf(
            topLeftRadius,
            topLeftRadius,
            topRightRadius,
            topRightRadius,
            bottomRightRadius,
            bottomRightRadius,
            bottomLeftRadius,
            bottomLeftRadius
        )
        path.addRoundRect(bounds, radii, Path.Direction.CW)
        val checkpoint = canvas.save()
        canvas.clipPath(path)
        try {
            callSuper()
            if (borderWidth > 0f) {
                paint.isAntiAlias = true
                paint.color = borderColor
                paint.strokeWidth = borderWidth
                paint.style = Paint.Style.STROKE
                canvas.drawPath(path, paint)
            }
        } finally {
            canvas.restoreToCount(checkpoint)
        }
    }

    override fun topLeftRadius(r: Float) {
        topLeftRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun topRightRadius(r: Float) {
        topRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun bottomLeftRadius(r: Float) {
        bottomLeftRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun bottomRightRadius(r: Float) {
        bottomRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun leftRadius(r: Float) {
        if (r == 0f) return
        topLeftRadius = r
        bottomLeftRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun rightRadius(r: Float) {
        if (r == 0f) return
        topRightRadius = r
        bottomRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun topRadius(r: Float) {
        if (r == 0f) return
        topLeftRadius = r
        topRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun bottomRadius(r: Float) {
        if (r == 0f) return
        bottomLeftRadius = r
        bottomRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun borderWidth(width: Float) {
        borderWidth = width * 2
        if (hasLoadAttrs) host.invalidate()
    }

    override fun borderColor(c: Int) {
        borderColor = c
        if (hasLoadAttrs) host.invalidate()
    }

    override fun radius(r: Float) {
        if (r == 0f) return
        topLeftRadius = r
        topRightRadius = r
        bottomLeftRadius = r
        bottomRightRadius = r
        if (hasLoadAttrs) host.invalidate()
    }

    override fun clearRadius() {
        topLeftRadius = 0f
        topRightRadius = 0f
        bottomLeftRadius = 0f
        bottomRightRadius = 0f
        if (hasLoadAttrs) host.invalidate()
    }
}