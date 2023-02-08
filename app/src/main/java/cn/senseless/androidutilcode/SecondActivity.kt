package cn.senseless.androidutilcode

import android.os.Bundle
import cn.senseless.androidutilcode.databinding.ActivitySecondBinding
import cn.senseless.scaffold.base.ScaffoldActivity
import cn.senseless.scaffold.utils.intentValue

class SecondActivity:ScaffoldActivity<ActivitySecondBinding>() {
    private val someStr by intentValue<String>("someStrKey")
    private val someObj by intentValue<String>("someStrKey")

    override fun getLayoutId(): Int {
        return R.layout.activity_second
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}