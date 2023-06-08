package cn.senseless.scaffold.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class ScaffoldDialogFragment<T : ViewDataBinding> : DialogFragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

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

    override fun onDestroyView() {
        if (enableEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        _binding = null
        super.onDestroyView()
    }
}