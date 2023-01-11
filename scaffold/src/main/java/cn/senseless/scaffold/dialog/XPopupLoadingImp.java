package cn.senseless.scaffold.dialog;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class XPopupLoadingImp implements ILoading {
    private final LoadingPopupView loadingPopupView;

    public XPopupLoadingImp(Context context) {
        loadingPopupView = new XPopup.Builder(context).asLoading();
    }

    @Override
    public boolean isLoadingShown() {
        return loadingPopupView.isShown();
    }

    @Override
    public void showLoading() {
        showLoading(null);
    }

    @Override
    public void showLoading(CharSequence message) {
        loadingPopupView.setTitle(message);
        loadingPopupView.show();
    }

    @Override
    public void dismissLoading() {
        loadingPopupView.dismiss();
    }

    @Override
    public void dismissLoading(long delay) {
        loadingPopupView.delayDismiss(delay);
    }
}
