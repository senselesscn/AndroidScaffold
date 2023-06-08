package cn.senseless.androidutilcode.video

import android.content.Context
import androidx.lifecycle.liveData
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import cn.senseless.androidutilcode.MediaSourceProvider
import cn.senseless.scaffold.model.ScaffoldViewModel
import cn.senseless.scaffold.model.State
import cn.senseless.scaffold.net.http
import java.util.LinkedList

class VideoViewModel : ScaffoldViewModel() {
    private val playerCache = LinkedList<ExoPlayer>()

    fun obtainPlayer(context: Context): ExoPlayer {
        var exoPlayer = playerCache.pollFirst()
        if (exoPlayer != null) return exoPlayer
        exoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(MediaSourceProvider.cacheMediaSourceFactory)
            .setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
            .build()
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        return exoPlayer
    }

    fun recyclePlayer(player: ExoPlayer?) {
        player ?: return
        player.stop()
        player.clearMediaItems()
        player.clearVideoSurface()
        player.playWhenReady = false
        playerCache.addLast(player)
    }

    fun releasePlayer() {
        playerCache.forEach {
            it.release()
        }
        playerCache.clear()
    }

    fun getVideoList(num: Int) = liveData<State<List<Video>>> {
        try {
            val resp: VideoResp =
                http("https://test.cloudgn.com/news_svc/v3/short_video_detail?video_id=3011&type=3&page_num=$num")
                    .get(
                        mapOf(
                            "dev_no" to "7dafeb82-d20e-4d74-8b0c-6c0e0d921886",
                            "sys_vno" to "10",
                            "app_src" to "2",
                            "app_vno" to "1.6.1",
                            "dev_res" to "1080X2292",
                        )
                    )
            if (resp.code == 200) {
                emit(State.Success(resp.result))
            } else {
                emit(State.Error(400, "接口异常"))
            }
        } catch (e: Exception) {
            emit(State.Error(500, "网络异常"))
        }
    }
}