package cn.senseless.androidutilcode

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.fragment.app.commit
import cn.senseless.androidutilcode.databinding.ActivityWebBinding
import cn.senseless.scaffold.base.ScaffoldActivity
import cn.senseless.scaffold.base.WebViewFragment
import cn.senseless.scaffold.utils.intentValue
import com.gyf.immersionbar.ImmersionBar

class WebActivity : ScaffoldActivity<ActivityWebBinding>(), WebViewFragment.WebViewCallback {
    private val url by intentValue<String>("url")

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        ImmersionBar.with(this)
            .titleBar(binding.toolbar)
            .statusBarDarkFont(true)
            .init()
        supportFragmentManager.commit {
            val webViewFragment = WebViewFragment()
            webViewFragment.setArguments("url" to url)
            replace(R.id.fragment_container, webViewFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onReceivedTitle(view: WebView, title: String?) {
        setTitle(title)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }
}