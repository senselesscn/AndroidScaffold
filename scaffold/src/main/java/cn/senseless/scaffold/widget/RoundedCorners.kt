package cn.senseless.scaffold.widget

interface RoundedCorners {

    fun topLeftRadius(r: Float)

    fun topRightRadius(r: Float)

    fun bottomLeftRadius(r: Float)

    fun bottomRightRadius(r: Float)

    fun leftRadius(r: Float)

    fun rightRadius(r: Float)

    fun topRadius(r: Float)

    fun bottomRadius(r: Float)

    fun borderWidth(width: Float)

    fun borderColor(c: Int)

    fun radius(r: Float)

    fun clearRadius()
}