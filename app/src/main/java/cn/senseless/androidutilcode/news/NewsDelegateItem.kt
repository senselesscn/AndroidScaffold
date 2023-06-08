package cn.senseless.androidutilcode.news

import android.view.View
import cn.senseless.androidutilcode.R
import cn.senseless.androidutilcode.WebActivity
import cn.senseless.androidutilcode.databinding.ItemNewsBinding
import cn.senseless.scaffold.adapter.DelegateBindingItem
import cn.senseless.scaffold.utils.startActivity
import com.bumptech.glide.Glide

class NewsDelegateItem : DelegateBindingItem<ItemNewsBinding, NewsResp.Result.News>() {

    init {
        listenClick(R.id.list_item)
    }

    override fun on(data: Any, position: Int): Boolean {
        return true
    }

    override fun bind(binding: ItemNewsBinding, data: NewsResp.Result.News, position: Int) {
        binding.data = data
        Glide.with(binding.ivCover)
            .load(data.picUrl)
            .into(binding.ivCover)
    }

    override fun onItemClickListener(view: View, data: NewsResp.Result.News, position: Int) {
        when (view.id) {
            R.id.list_item -> {
                context.startActivity<WebActivity>("url" to data.url)
            }
        }
    }
}