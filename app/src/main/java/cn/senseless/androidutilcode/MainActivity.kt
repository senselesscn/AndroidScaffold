package cn.senseless.androidutilcode

import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.fragment.app.transaction
import cn.senseless.androidutilcode.databinding.ActivityMainBinding
import cn.senseless.scaffold.base.ScaffoldActivity
import cn.senseless.scaffold.utils.doOnProgressChanged
import cn.senseless.scaffold.utils.onClick

class MainActivity : ScaffoldActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.commit {
            val homeFragment = HomeFragment()
            homeFragment.setArguments("text" to "Hello World")
            add(R.id.fragment_container, homeFragment)
        }
        findViewById<Toolbar>(R.id.toolbar).onClick(400) {
            Log.d(TAG, "initView: ")
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}