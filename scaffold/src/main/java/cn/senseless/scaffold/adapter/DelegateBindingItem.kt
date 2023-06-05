package cn.senseless.scaffold.adapter

import android.view.View
import androidx.databinding.ViewDataBinding

abstract class DelegateBindingItem<B : ViewDataBinding, T> : DelegateItem<T>() {
    private var _binding: B? = null
    val binding: B
        get() {
            return _binding!!
        }


    override fun bind(holder: DelegateViewHolder, data: T) {
        if (_binding == null) {
            val kTypeParameter = this::class.typeParameters[0]
            val clazz = Class.forName(kTypeParameter.name)
            val method = clazz.getMethod("bind", View::class.java)
            _binding = method.invoke(null, holder.itemView) as B
        }
    }

    abstract fun bind(holder: DelegateBindingViewHolder<B>, data: T)
}