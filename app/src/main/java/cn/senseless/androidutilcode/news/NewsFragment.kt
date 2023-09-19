package cn.senseless.androidutilcode.news

import android.os.Bundle
import androidx.fragment.app.viewModels
import cn.senseless.androidutilcode.databinding.FragmentNewsBinding
import cn.senseless.scaffold.adapter.DelegateAdapter
import cn.senseless.scaffold.base.ScaffoldFragment

class NewsFragment : ScaffoldFragment<FragmentNewsBinding>() {
    private val viewModel by viewModels<NewsViewModel>()

    @OptIn(ExperimentalStdlibApi::class)
    override fun initView(savedInstanceState: Bundle?) {
        binding.rvNews.adapter = DelegateAdapter(NewsDelegateItem())
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        binding.pagingLayout.autoRefresh()
    }
}