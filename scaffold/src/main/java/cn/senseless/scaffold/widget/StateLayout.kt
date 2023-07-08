package cn.senseless.scaffold.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import cn.senseless.scaffold.R

@ExperimentalStdlibApi
class StateLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), OnClickListener {
    private var contentView: View? = null
    private var errorView: View? = null
    private var loadingView: View? = null
    private var emptyView: View? = null
    private val errorLayoutId: Int
    private val emptyLayoutId: Int
    private var loadingLayoutId: Int
    private var onStateViewClickListener: ((id: Int) -> Unit)? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        errorLayoutId = a.getLayoutDimension(
            R.styleable.StateLayout_errorLayoutId,
            R.layout.sca_def_error_place_hoder
        )
        emptyLayoutId = a.getLayoutDimension(
            R.styleable.StateLayout_emptyLayoutId,
            R.layout.sca_def_empty_place_hoder
        )
        loadingLayoutId = a.getLayoutDimension(
            R.styleable.StateLayout_loadingLayoutId,
            R.layout.sca_def_loading_place_hoder
        )
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            contentView = getChildAt(0)
        }
    }

    fun error(text: String?) {
        loadingView?.let {
            removeView(it)
            loadingView = null
        }
        emptyView?.let {
            removeView(it)
            emptyView = null
        }
        contentView?.isVisible = false
        if (errorView == null && errorLayoutId != 0) {
            errorView = LayoutInflater.from(context).inflate(errorLayoutId, this, false)
            errorView!!.setOnClickListener(this)
            errorView!!.findViewById<TextView>(R.id.tv_text)?.let {
                it.text = text
            }
            addView(errorView)
        }
    }

    fun loading() {
        emptyView?.let {
            removeView(it)
            emptyView = null
        }
        errorView?.let {
            removeView(it)
            errorView = null
        }
        contentView?.isVisible = false
        if (loadingView == null && loadingLayoutId != 0) {
            loadingView = LayoutInflater.from(context).inflate(loadingLayoutId, this, false)
            loadingView!!.setOnClickListener(this)
            addView(loadingView)
        }
    }

    fun success() {
        loadingView?.let {
            removeView(it)
            loadingView = null
        }
        emptyView?.let {
            removeView(it)
            emptyView = null
        }
        errorView?.let {
            removeView(it)
            errorView = null
        }
        contentView?.isVisible = true
    }

    fun empty() {
        loadingView?.let {
            removeView(it)
            loadingView = null
        }
        errorView?.let {
            removeView(it)
            errorView = null
        }
        contentView?.isVisible = false
        if (emptyView == null && emptyLayoutId != 0) {
            emptyView = LayoutInflater.from(context).inflate(emptyLayoutId, this, false)
            emptyView!!.setOnClickListener(this)
            addView(emptyView)
        }
    }

    fun setOnStateViewClickListener(listener: ((id: Int) -> Unit)?) {
        onStateViewClickListener = listener
    }

    override fun onClick(v: View) {
        onStateViewClickListener?.invoke(v.id)
    }
}