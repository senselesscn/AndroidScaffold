package cn.senseless.scaffold.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import java.lang.reflect.ParameterizedType

abstract class DelegateBindingItem<B : ViewDataBinding, T : Any> : DelegateItem<T>() {
    override val layoutId: Int
        get() = 0

    @Suppress("UNCHECKED_CAST")
    override fun onCreateHolder(parent: ViewGroup): DelegateViewHolder {
        context = parent.context
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val method = (type as Class<*>).getMethod(
            "inflate", LayoutInflater::class.java,
            ViewGroup::class.java, Boolean::class.java
        )
        val binding = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as B
        val delegateBindingViewHolder = DelegateViewHolder(binding.root)
        delegateBindingViewHolder.binding = binding
        delegateBindingViewHolder.delegateItem = this as DelegateItem<Any>
        return delegateBindingViewHolder
    }

    @Suppress("UNCHECKED_CAST")
    override fun bind(holder: DelegateViewHolder, data: T, position: Int) {
        bind(holder.binding as B, data, position)
    }

    abstract fun bind(binding: B, data: T, position: Int)
}