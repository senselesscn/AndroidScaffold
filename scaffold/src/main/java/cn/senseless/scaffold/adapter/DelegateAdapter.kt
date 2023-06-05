package cn.senseless.scaffold.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter : RecyclerView.Adapter<DelegateViewHolder>() {
    val data: List<Any>
        get() = dataList
    private val dataList = ArrayList<Any>()
    private val delegates = ArrayList<DelegateItem<Any>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateViewHolder {
        val delegateItem = delegates[viewType]
        val view = LayoutInflater.from(parent.context).inflate(delegateItem.layoutId, parent, false)
        return DelegateViewHolderImpl(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, position: Int) {
        val data = dataList[position]
        val delegate =
            delegates.find { it.on(data, position) } ?: throw Exception("no delegate found in Adapter#onBindViewHolder")
        delegate.bind(holder, data)
    }

    override fun getItemViewType(position: Int): Int {
        val data = dataList[position]
        delegates.forEachIndexed { index, delegateItem ->
            if (delegateItem.on(data, position)) {
                return index
            }
        }
        throw Exception("no delegate found in Adapter#getItemViewType")
    }

    fun addDelegate(delegate: DelegateItem<*>) {
        delegates.add(delegate as DelegateItem<Any>)
    }

    fun removeDelegate(delegate: DelegateItem<*>) {
        delegates.remove(delegate)
    }

    fun addData(collection: Collection<Any>) {
        dataList.addAll(collection)
        notifyItemRangeInserted(dataList.size - collection.size, collection.size)
    }

    fun setData(collection: Collection<Any>) {
        dataList.clear()
        dataList.addAll(collection)
        notifyDataSetChanged()
    }

}