package cn.senseless.scaffold.base

import android.webkit.WebView

interface WebViewCallback {

    fun onReceivedTitle(view: WebView, title: String?){

    }

    fun onProgressChanged(view: WebView, newProgress: Int) {

    }

}