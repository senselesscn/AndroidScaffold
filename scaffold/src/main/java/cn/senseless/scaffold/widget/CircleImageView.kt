package cn.senseless.scaffold.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import cn.senseless.scaffold.R
import cn.senseless.scaffold.utils.clipOval

open class CircleImageView : AppCompatImageView {
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
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
        borderColor = a.getColor(R.styleable.CircleImageView_borderColor, Color.TRANSPARENT)
        borderWidth = a.getDimension(R.styleable.CircleImageView_borderWidth, 0f)
        a.recycle()
        scaleType = ScaleType.CENTER_CROP
        clipOval()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (borderColor != Color.TRANSPARENT && borderWidth > 0f) {
            paint.strokeWidth = borderWidth * 2
            paint.color = borderColor
            paint.style = Paint.Style.STROKE
            val w = width.toFloat()
            val h = height.toFloat()
            canvas.drawCircle(w / 2, h / 2, minOf(w / 2, h / 2), paint)
        }
    }
}