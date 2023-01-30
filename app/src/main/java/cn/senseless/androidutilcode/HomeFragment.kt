package cn.senseless.androidutilcode

import android.os.Bundle
import cn.senseless.androidutilcode.databinding.FragmentHomeBinding
import cn.senseless.scaffold.base.ScaffoldFragment
import cn.senseless.scaffold.utils.argumentsValue

class HomeFragment : ScaffoldFragment<FragmentHomeBinding>() {
    private val text by argumentsValue<String>("text")

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvText.text = text
    }
}