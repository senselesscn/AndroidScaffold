package cn.senseless.scaffold.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.senseless.scaffold.base.ScaffoldActivity
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class ScaffoldDialogFragment<T : ViewDataBinding> : DialogFragment(), ILoading {
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        _binding = null
        super.onDestroyView()
    }

    fun show(manager: FragmentManager) {
        show(manager, javaClass.simpleName)
    }

    fun showNow(manager: FragmentManager) {
        showNow(manager, javaClass.simpleName)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        accessFlag()
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        accessFlag()
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitNowAllowingStateLoss()
    }

    private fun accessFlag() {
        val clazz = DialogFragment::class.java
        val mDismissed = clazz.getDeclaredField("mDismissed")
        mDismissed.isAccessible = true
        mDismissed.setBoolean(this, false)
        val mShownByMe = clazz.getDeclaredField("mShownByMe")
        mShownByMe.isAccessible = true
        mShownByMe.setBoolean(this, true)
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
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
}