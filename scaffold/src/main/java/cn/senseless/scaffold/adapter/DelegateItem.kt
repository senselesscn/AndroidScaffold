package cn.senseless.scaffold.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import cn.senseless.scaffold.utils.getDrawableCompat

abstract class DelegateItem<T : Any> {
    abstract val layoutId: Int
    lateinit var context: Context
        internal set
    private var listenClickIds = intArrayOf()

    open fun onCreateHolder(parent: ViewGroup): DelegateViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val delegateViewHolder = DelegateViewHolder(view)
        delegateViewHolder.delegateItem = this as DelegateItem<Any>
        return delegateViewHolder
    }

    abstract fun on(data: Any, position: Int): Boolean

    internal open fun _bind(holder: DelegateViewHolder, data: T, position: Int) {
        listenClickIds.forEach {
            val view = holder.getView<View>(it)
            view?.setOnClickListener {
                onItemClickListener(view, data, position)
            }
        }
        bind(holder, data, position)
    }

    abstract fun bind(holder: DelegateViewHolder, data: T, position: Int)


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

    fun listenClick(@IdRes vararg id: Int) {
        listenClickIds = id
    }

    open fun onItemClickListener(view: View, data: T, position: Int) {

    }
}