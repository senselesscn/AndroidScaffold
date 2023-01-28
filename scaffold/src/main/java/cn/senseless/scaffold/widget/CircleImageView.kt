package cn.senseless.scaffold.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.withClip
import cn.senseless.scaffold.R

/**
 * 直接画布切圆，宽高需要相等
 */
class CircleImageView : AppCompatImageView {
    private val path = Path()
    var borderColor = Color.WHITE
    var borderWidth = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val point = PointF()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        loadAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        loadAttrs(attrs)
    }

    private fun loadAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
        borderColor = a.getColor(R.styleable.CircleImageView_borderColor, 0)
        borderWidth = a.getDimension(R.styleable.CircleImageView_borderWidth, 0f)
        a.recycle()
    }

    override fun draw(canvas: Canvas) {
        if (canvas.drawFilter == null) {
            canvas.drawFilter = PaintFlagsDrawFilter(
                0,
                Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
            )
        }
        path.reset()
        point.set(width / 2f, height / 2f)
        path.addCircle(point.x, point.y, point.y, Path.Direction.CW)
        canvas.withClip(path) {
            super.draw(canvas)
            if (borderWidth > 0f) {
                paint.color = borderColor
                paint.strokeWidth = borderWidth * 2
                paint.style = Paint.Style.STROKE
                canvas.drawPath(path, paint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w = 0
        var h = 0
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthSpecMode == MeasureSpec.EXACTLY) {
            w = MeasureSpec.getSize(widthMeasureSpec)
            h = w
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            h = MeasureSpec.getSize(heightMeasureSpec)
            w = h
        }
        setMeasuredDimension(w, h)

        //Log.d(TAG, "onMeasure: measuredHeight = $measuredHeight,measuredWidth = $measuredWidth")
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        //disable
    }
}