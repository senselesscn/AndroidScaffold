package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cn.senseless.scaffold.dialog.ILoading
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class ScaffoldFragment<T : ViewDataBinding> : Fragment(), ILoading {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!
    private var lazyLoadFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val method = (type as Class<*>).getMethod(
            "inflate", LayoutInflater::class.java,
            ViewGroup::class.java, Boolean::class.java
        )
        @Suppress("UNCHECKED_CAST")
        _binding = method.invoke(null, inflater, container, false) as T
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        loadData(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!lazyLoadFlag) {
            lazyLoadFlag = true
            lazyLoad()
        }
    }

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

    open fun lazyLoad() {

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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        _binding = null
        super.onDestroyView()
    }

}