package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.senseless.scaffold.R
import cn.senseless.scaffold.dialog.ILoading
import cn.senseless.scaffold.dialog.XPopupLoadingImp
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), ILoading {
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

    abstract fun getLayoutId(): Int

    @CallSuper
    open fun beforeInitView(savedInstanceState: Bundle?) {
        _binding = DataBindingUtil.setContentView(this, getLayoutId())
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
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (enableEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        dismissLoading()
    }
}