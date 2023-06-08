package cn.senseless.scaffold.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class PagingLayout : SmartRefreshLayout, OnRefreshLoadMoreListener {
    private var index = startIndex
    private var nextIndex = startIndex
    var onLoadData: ((Int) -> Unit)? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setOnRefreshLoadMoreListener(this)
    }

    /**
     * @param data 服务器返回的数组
     */
    fun addData(
        data: List<Any>?,
        adapter: DelegateAdapter? = null,
        hasMore: () -> Boolean = { !data.isNullOrEmpty() }
    ) {
        var adpt: DelegateAdapter? = adapter
        if (adpt == null) {
            val recyclerView = children.find { it is RecyclerView } as? RecyclerView
            adpt = recyclerView?.adapter as? DelegateAdapter
        }
        adpt ?: throw IllegalStateException("无法获取到DelegateAdapter")
        if (nextIndex == startIndex) {
            adpt.setData(data ?: emptyList())
        } else {
            adpt.addData(data ?: emptyList())
        }
        index = nextIndex
        finish(hasMore = hasMore())
    }

    fun finish(success: Boolean = true, hasMore: Boolean = true) {
        if (isLoading) {
            if (hasMore) {
                finishLoadMore(success)
            } else {
                finishLoadMoreWithNoMoreData()
            }
        }
        if (isRefreshing) {
            if (hasMore) {
                finishRefresh(success)
            } else {
                finishRefreshWithNoMoreData()
            }
        }
    }

    companion object {
        var startIndex = 1
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        nextIndex = startIndex
        onLoadData?.invoke(nextIndex)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        nextIndex = index + 1
        onLoadData?.invoke(nextIndex)
    }
}