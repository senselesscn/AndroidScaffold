package cn.senseless.androidutilcode

import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import cn.senseless.scaffold.utils.ApplicationHolder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

object MediaSourceProvider {
    private const val maxCacheSize: Long = 512 * 1024 * 1024

    private val okHttpClient: OkHttpClient by lazy(LazyThreadSafetyMode.NONE) {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC//绝对不能使用body级别！
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    val cacheMediaSourceFactory: MediaSource.Factory by lazy(LazyThreadSafetyMode.NONE) {
        val context = ApplicationHolder.appContext
        val cacheFile = File(context.externalCacheDir, "player-cache")
        val databaseProvider = StandaloneDatabaseProvider(context)
        val cacheEvictor = LeastRecentlyUsedCacheEvictor(maxCacheSize)
        val simpleCache = SimpleCache(cacheFile, cacheEvictor, databaseProvider)
        val cacheDataSourceFactory = CacheDataSource.Factory()
        cacheDataSourceFactory.setCache(simpleCache)
        cacheDataSourceFactory.setUpstreamDataSourceFactory(OkHttpDataSource.Factory(okHttpClient))
        ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }
}