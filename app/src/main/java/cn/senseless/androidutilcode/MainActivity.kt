package cn.senseless.androidutilcode

import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import cn.senseless.androidutilcode.databinding.ActivityMainBinding
import cn.senseless.scaffold.base.BaseActivity
import cn.senseless.scaffold.utils.doOnProgressChanged

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        binding.editText.doAfterTextChanged {

        }
        binding.seekbar.doOnProgressChanged { seekBar, progress, fromUser ->
            Log.d(TAG, "initView: $progress")
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}