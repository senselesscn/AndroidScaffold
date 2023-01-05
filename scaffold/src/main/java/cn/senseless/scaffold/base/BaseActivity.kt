package cn.senseless.scaffold.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.senseless.scaffold.R
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T
        private set
    private lateinit var loadingDialog: LoadingPopupView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView(savedInstanceState)
        initView(savedInstanceState)
        afterInitView(savedInstanceState)
        beforeLoadData(savedInstanceState)
        loadData(savedInstanceState)
        afterLoadData(savedInstanceState)
    }

    abstract fun getLayoutId(): Int

    open fun beforeInitView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        val toolbar = findViewById<View>(R.id.toolbar)
        if (toolbar != null && toolbar is Toolbar) {
        }
        ImmersionBar.with(this)
            .navigationBarDarkIcon(true)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.white)
            .titleBar(toolbar)
            .init()
        loadingDialog = XPopup.Builder(this).asLoading()
    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun afterInitView(savedInstanceState: Bundle?) {
    }

    open fun beforeLoadData(savedInstanceState: Bundle?) {
        if (enableEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    abstract fun loadData(savedInstanceState: Bundle?)

    open fun afterLoadData(savedInstanceState: Bundle?) {}

    open fun enableEventBus() = false

    fun showLoading() {
        loadingDialog.show()
    }

    fun dismissLoading() {
        loadingDialog.dismiss()
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