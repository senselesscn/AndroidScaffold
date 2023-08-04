package cn.senseless.androidutilcode

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.senseless.androidutilcode.databinding.ActivityMainBinding
import cn.senseless.scaffold.base.ScaffoldActivity
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.delay
import org.greenrobot.eventbus.EventBus
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : ScaffoldActivity<ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}