package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cn.senseless.scaffold.dialog.ILoading
import org.greenrobot.eventbus.EventBus

abstract class ScaffoldFragment<T : ViewDataBinding> : Fragment(), ILoading {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        loadData(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    @CallSuper
    open fun beforeInitView(savedInstanceState: Bundle?) {
    }

    abstract fun initView(savedInstanceState: Bundle?)

    @CallSuper
    open fun loadData(savedInstanceState: Bundle?) {
        if (enableEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun enableEventBus() = false

    fun setArguments(vararg args: Pair<String, Any?>) {
        super.setArguments(bundleOf(*args))
    }

    override fun dismissLoading() {
        (activity as ScaffoldActivity<*>).dismissLoading()
    }

    override fun showLoading() {
        (activity as ScaffoldActivity<*>).showLoading()
    }

    override fun isLoadingShown(): Boolean {
        return (activity as ScaffoldActivity<*>).isLoadingShown
    }

    override fun showLoading(message: CharSequence?) {
        (activity as ScaffoldActivity<*>).showLoading(message)
    }

    override fun dismissLoading(delay: Long) {
        (activity as ScaffoldActivity<*>).dismissLoading(delay)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        if (enableEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

}