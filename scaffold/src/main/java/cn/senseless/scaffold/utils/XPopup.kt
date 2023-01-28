package cn.senseless.scaffold.utils

import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView


fun BasePopupView.showDialog(builder: XPopup.Builder.() -> Unit) {
    XPopup.Builder(context).apply(builder)
        .asCustom(this)
        .show()
}

fun BasePopupView.showDialog() {
    XPopup.Builder(context)
        .asCustom(this)
        .show()
}