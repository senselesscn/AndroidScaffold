package cn.senseless.androidutilcode.video

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import cn.senseless.androidutilcode.databinding.FragmentVideoListBinding
import cn.senseless.scaffold.base.ScaffoldFragment
import cn.senseless.scaffold.model.State
import cn.senseless.scaffold.utils.addOnPageChangeListener

class VideoListFragment : ScaffoldFragment<FragmentVideoListBinding>() {
    private lateinit var adapter: VideoFragmentAdapter
    private var pageNum = 1
    private var videos = ArrayList<Video>()
    private val model by activityViewModels<VideoViewModel>()

    override fun initView(savedInstanceState: Bundle?) {
        adapter = VideoFragmentAdapter(this, videos)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(
            onPageSelected = {
                if (it == videos.size - 1) {
                    loadVideo(pageNum + 1)
                }
            }
        )
    }


    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        loadVideo(pageNum)
    }

    private fun loadVideo(pageNum: Int) {
    }

    override fun onDetach() {
        super.onDetach()
        model.releasePlayer()
    }
}