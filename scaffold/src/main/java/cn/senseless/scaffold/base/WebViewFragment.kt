package cn.senseless.scaffold.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.senseless.scaffold.databinding.ScaFragmentWebviewBinding
import cn.senseless.scaffold.utils.WebViewManager
import cn.senseless.scaffold.utils.argumentsValue

open class WebViewFragment : ScaffoldFragment<ScaFragmentWebviewBinding>(), WebViewCallback {
    private var webView: WebView? = null
    private val url by argumentsValue<String>("url")

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        webView = WebViewManager.obtain(requireContext()).apply {
            webViewClient = object : WebViewClient() {}
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String?) {
                    super.onReceivedTitle(view, title)
                    this@WebViewFragment.onReceivedTitle(view, title)
                }

                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    this@WebViewFragment.onProgressChanged(view, newProgress)
                }
            }
            binding.container.addView(
                this, 0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        if (!url.isNullOrEmpty()) {
            webView?.loadUrl(url!!)
        }
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun onDestroyView() {
        webView?.let { WebViewManager.recycle(it) }
        webView = null
        super.onDestroyView()
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        (activity as? WebViewCallback)?.onProgressChanged(view, newProgress)
    }

    override fun onReceivedTitle(view: WebView, title: String?) {
        (activity as? WebViewCallback)?.onReceivedTitle(view, title)
    }
}