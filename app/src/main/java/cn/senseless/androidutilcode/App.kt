package cn.senseless.androidutilcode

import android.app.Application
import cn.senseless.scaffold.utils.ApplicationHolder
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationHolder.init(this)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
    }
}