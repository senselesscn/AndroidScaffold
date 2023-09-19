package cn.senseless.scaffold.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.senseless.scaffold.databinding.ScaFragmentWebviewBinding
import cn.senseless.scaffold.utils.WebViewManager
import cn.senseless.scaffold.utils.argumentsValue

open class WebViewFragment : ScaffoldFragment<ScaFragmentWebviewBinding>() {
    private var webView: WebView? = null
    private var callback: WebViewCallback? = null
    private val url by argumentsValue<String>("url")

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        webView = WebViewManager.obtain(requireContext()).apply {
            webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return callback?.shouldOverrideUrlLoading(view, request)
                        ?: super.shouldOverrideUrlLoading(view, request)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    callback?.onReceivedError(view, request, error)
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String?) {
                    super.onReceivedTitle(view, title)
                    callback?.onReceivedTitle(view, title)
                }

                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    callback?.onProgressChanged(view, newProgress)
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

    fun setWebViewCallback(callback: WebViewCallback?) {
        this.callback = callback
    }

    interface WebViewCallback {

        fun onReceivedTitle(view: WebView, title: String?)

        fun onProgressChanged(view: WebView, newProgress: Int)

        fun onReceivedError(
            view: WebView?, request: WebResourceRequest?, error: WebResourceError?
        )

        fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
    }
}