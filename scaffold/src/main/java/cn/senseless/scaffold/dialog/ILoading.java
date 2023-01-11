package cn.senseless.scaffold.dialog;

public interface ILoading {

    boolean isLoadingShown();

    void showLoading();

    void showLoading(CharSequence message);

    void dismissLoading();

    void dismissLoading(long delay);
}
