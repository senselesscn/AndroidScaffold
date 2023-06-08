package cn.senseless.androidutilcode.news

import androidx.lifecycle.liveData
import cn.senseless.scaffold.model.ScaffoldViewModel
import cn.senseless.scaffold.net.http

class NewsViewModel : ScaffoldViewModel() {
    fun getNews(page: Int) = liveData {
        val key = ""
        val resp = http("https://apis.tianapi.com/social/index?key=$key&num=10&page=$page")
            .get<NewsResp>()
        emit(resp)
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}