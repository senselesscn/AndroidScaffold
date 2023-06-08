package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import cn.senseless.scaffold.R
import cn.senseless.scaffold.dialog.ILoading
import cn.senseless.scaffold.dialog.XPopupLoadingImp
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class ScaffoldActivity<T : ViewDataBinding> : AppCompatActivity(), ILoading {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!
    private lateinit var loading: ILoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        loadData(savedInstanceState)
    }

    @CallSuper
    open fun beforeInitView(savedInstanceState: Bundle?) {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val method = (type as Class<*>).getMethod("inflate", LayoutInflater::class.java)
        @Suppress("UNCHECKED_CAST")
        _binding = method.invoke(null, layoutInflater) as T
        setContentView(binding.root)
        val toolbar = findViewById<View>(R.id.toolbar)
        if (toolbar != null && toolbar is Toolbar && supportActionBar == null) {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayShowHomeEnabled(false)
                it.setDisplayHomeAsUpEnabled(false)
            }
        }
        loading = getILoadingImp()
    }

    open fun getILoadingImp(): ILoading {
        return XPopupLoadingImp(this)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    @CallSuper
    open fun loadData(savedInstanceState: Bundle?) {
        if (enableEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun enableEventBus() = false

    override fun showLoading() {
        loading.showLoading()
    }

    override fun dismissLoading() {
        loading.dismissLoading()
    }

    override fun isLoadingShown(): Boolean {
        return loading.isLoadingShown
    }

    override fun showLoading(message: CharSequence?) {
        loading.showLoading(message)
    }

    override fun dismissLoading(delay: Long) {
        loading.dismissLoading(delay)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        dismissLoading()
    }
}