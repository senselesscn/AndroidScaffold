package cn.senseless.androidutilcode.video

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoFragmentAdapter(fragment: Fragment, private val data: List<Video>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        val videoFragment = VideoFragment()
        videoFragment.setArguments("video" to data[position])
        return videoFragment
    }

}