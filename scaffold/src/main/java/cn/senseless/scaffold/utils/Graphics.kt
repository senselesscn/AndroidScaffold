@file:JvmName("GraphicsUtils")

package cn.senseless.scaffold.utils

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.drawTextInCenter(text: String, x: Float, y: Float, paint: Paint) {
    val textAlign = paint.textAlign
    val fm = paint.fontMetrics
    val centerLine = y + (fm.bottom - fm.top) / 2 - fm.bottom
    paint.textAlign = Paint.Align.CENTER
    drawText(text, x, centerLine, paint)
    if (textAlign != Paint.Align.CENTER) paint.textAlign = textAlign
}