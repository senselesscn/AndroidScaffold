package cn.senseless.scaffold.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import cn.senseless.scaffold.R
import cn.senseless.scaffold.utils.clipRadius

/**
 * 不支持单独设置圆角，但无锯齿、支持阴影
 */
open class RoundTextView : AppCompatTextView {
    private var radius = 0f
    private var borderWidth = 0f
    private var borderColor = Color.TRANSPARENT
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context){
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView)
        radius = a.getDimension(R.styleable.RoundTextView_radius, 0f)
        borderColor = a.getColor(R.styleable.RoundTextView_borderColor, Color.TRANSPARENT)
        borderWidth = a.getDimension(R.styleable.RoundTextView_borderWidth, 0f)
        a.recycle()
        clipRadius(radius)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (borderColor != Color.TRANSPARENT && borderWidth > 0f) {
            paint.strokeWidth = borderWidth * 2
            paint.color = borderColor
            paint.style = Paint.Style.STROKE
            val w = width.toFloat()
            val h = height.toFloat()
            canvas.drawRoundRect(
                0f, 0f, w, h,
                radius, radius, paint
            )
        }
    }
}