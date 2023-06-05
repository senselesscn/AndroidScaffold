package cn.senseless.androidutilcode

import android.os.Build
import android.os.Bundle
import cn.senseless.androidutilcode.databinding.FragmentHomeBinding
import cn.senseless.scaffold.base.ScaffoldFragment
import cn.senseless.scaffold.net.http
import cn.senseless.scaffold.utils.argumentsValue
import cn.senseless.scaffold.utils.clipOval
import cn.senseless.scaffold.utils.clipRadius
import cn.senseless.scaffold.utils.dp
import cn.senseless.scaffold.utils.launch

class HomeFragment : ScaffoldFragment<FragmentHomeBinding>() {
    private val text by argumentsValue<String>("text")

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvText.text = text
        binding.vOval.clipOval()
        binding.vRound.clipRadius(16f.dp)
        launch {
            binding.vRound.text = http("https://www.runoob.com/aboutus").get()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.vRound2.clipRadius(16f.dp, 0f, 16.dp, 0f)
        }
    }
}