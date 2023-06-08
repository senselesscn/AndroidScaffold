package cn.senseless.androidutilcode.news

import android.os.Bundle
import androidx.fragment.app.viewModels
import cn.senseless.androidutilcode.databinding.FragmentNewsBinding
import cn.senseless.scaffold.adapter.DelegateAdapter
import cn.senseless.scaffold.base.ScaffoldFragment

class NewsFragment : ScaffoldFragment<FragmentNewsBinding>() {
    private val viewModel by viewModels<NewsViewModel>()

    override fun initView(savedInstanceState: Bundle?) {
        binding.pagingLayout.onLoadData = {
            viewModel.getNews(it).observe(viewLifecycleOwner) { resp ->
                if (resp.code == 200) {
                    binding.pagingLayout.addData(resp.result?.newslist)
                } else {
                    binding.pagingLayout.finish(false)
                }
            }
        }
        binding.rvNews.adapter = DelegateAdapter(NewsDelegateItem())
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        binding.pagingLayout.autoRefresh()
    }
}