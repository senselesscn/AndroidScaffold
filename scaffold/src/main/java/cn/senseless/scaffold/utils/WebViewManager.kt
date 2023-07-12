package cn.senseless.scaffold.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.MutableContextWrapper
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.LinkedList

object WebViewManager {
    private const val TAG = "WebViewManager"
    private val idleWebViews = LinkedList<WebView>()

    fun recycle(webView: WebView) {
        webView.stopLoading()
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webView.clearHistory()
        webView.webViewClient = object : WebViewClient() {}
        webView.webChromeClient = object : WebChromeClient() {}
        (webView.parent as? ViewGroup)?.removeView(webView)
        (webView.context as MutableContextWrapper).baseContext = webView.context.applicationContext
        idleWebViews.add(webView)
    }

    fun obtain(context: Context): WebView {
        val webView = idleWebViews.pollFirst()
        if (webView != null) {
            Log.d(TAG, "obtain: 使用缓存webView")
            (webView.context as MutableContextWrapper).baseContext = context
            return webView
        }
        Log.d(TAG, "obtain: 使用新webView")
        return WebView(MutableContextWrapper(context)).also {
            it.settings.apply {
                @SuppressLint("SetJavaScriptEnabled")
                javaScriptEnabled = true
                useWideViewPort = true
                allowContentAccess = true
                allowContentAccess = true
                allowFileAccess = true
                domStorageEnabled = true
            }
        }
    }

    /**
     * 如果一段时间内不会用到WebView了，那么可以手动释放
     */
    fun release() {
        for (webView in idleWebViews) {
            webView.destroy()
        }
        idleWebViews.clear()
    }
}