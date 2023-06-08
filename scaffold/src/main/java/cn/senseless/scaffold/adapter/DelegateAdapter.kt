@file:Suppress("UNCHECKED_CAST")

package cn.senseless.scaffold.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter() : RecyclerView.Adapter<DelegateViewHolder>() {
    val data: List<Any>
        get() = dataList
    private val dataList = ArrayList<Any>()
    private val delegates = ArrayList<DelegateItem<Any>>()

    constructor(vararg delegate: DelegateItem<*>) : this() {
        delegates.addAll(delegate.map {
            it as DelegateItem<Any>
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateViewHolder {
        val delegateItem = delegates[viewType]
        return delegateItem.onCreateHolder(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, position: Int) {
        val data = dataList[position]
        holder.delegateItem._bind(holder, data, position)
    }

    override fun getItemViewType(position: Int): Int {
        val data = dataList[position]
        delegates.forEachIndexed { index, delegateItem ->
            if (delegateItem.on(data, position)) {
                return index
            }
        }
        throw RuntimeException("no delegate found in Adapter#getItemViewType")
    }

    fun <T : Any> addDelegate(delegate: DelegateItem<T>) {
        delegates.add(delegate as DelegateItem<Any>)
    }

    fun removeDelegate(delegate: DelegateItem<*>) {
        delegates.remove(delegate)
    }

    fun addData(collection: Collection<Any>) {
        if (collection.isEmpty()) return
        dataList.addAll(collection)
        notifyItemRangeInserted(dataList.size - collection.size, collection.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(collection: Collection<Any>) {
        if (collection.isEmpty()) return
        dataList.clear()
        dataList.addAll(collection)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

}