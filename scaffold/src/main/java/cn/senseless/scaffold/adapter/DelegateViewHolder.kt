package cn.senseless.scaffold.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.senseless.scaffold.utils.getDrawableCompat

abstract class DelegateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context
    private val views = SparseArray<View>()


    fun getDrawable(@DrawableRes id: Int): Drawable {
        return context.getDrawableCompat(id)
    }

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getColorStateList(@ColorRes id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, id)
    }

    fun <T : View> getView(@IdRes id: Int): T? {
        var view = views[id]
        if (view == null) {
            view = itemView.findViewById(id)
            views[id] = view
        }
        return view as? T
    }

    fun setText(@IdRes id: Int, text: CharSequence?) {
        getView<TextView>(id)?.text = text
    }

}