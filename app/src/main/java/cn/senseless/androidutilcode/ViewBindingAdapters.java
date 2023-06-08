package cn.senseless.androidutilcode;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ViewBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void imageUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) return;
        Glide.with(imageView).load(url).into(imageView);
    }
}
