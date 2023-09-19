package cn.senseless.androidutilcode.video

import android.content.Context
import androidx.lifecycle.liveData
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import cn.senseless.androidutilcode.MediaSourceProvider
import cn.senseless.scaffold.model.ScaffoldViewModel
import cn.senseless.scaffold.model.State
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

}