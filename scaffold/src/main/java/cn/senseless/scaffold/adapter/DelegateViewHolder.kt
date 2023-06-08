package cn.senseless.scaffold.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DelegateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context
    private val views = SparseArray<View>()
    internal var binding: ViewDataBinding? = null
    internal lateinit var delegateItem: DelegateItem<Any>


    @Suppress("UNCHECKED_CAST")
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