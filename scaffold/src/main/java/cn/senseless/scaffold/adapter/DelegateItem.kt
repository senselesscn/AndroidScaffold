package cn.senseless.scaffold.adapter

abstract class DelegateItem<T> {
    abstract val layoutId: Int

    abstract fun on(data: Any, position: Int): Boolean

    abstract fun bind(holder: DelegateViewHolder, data: T)
}