package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        afterInitView(savedInstanceState)
        beforeLoadData(savedInstanceState)
        loadData(savedInstanceState)
        afterLoadData(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    open fun beforeInitView(savedInstanceState: Bundle?) {
    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun afterInitView(savedInstanceState: Bundle?) {}

    open fun beforeLoadData(savedInstanceState: Bundle?) {
        if (enableEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    abstract fun loadData(savedInstanceState: Bundle?)

    open fun afterLoadData(savedInstanceState: Bundle?) {}

    open fun enableEventBus() = false

    fun dismissLoading() {
        (activity as BaseActivity<*>).dismissLoading()
    }

    fun showLoading() {
        (activity as BaseActivity<*>).showLoading()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        if (enableEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

}