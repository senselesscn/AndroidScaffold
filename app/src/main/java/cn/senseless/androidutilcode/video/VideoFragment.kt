package cn.senseless.androidutilcode.video

import android.os.Bundle
import android.util.Log
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.liveData
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import cn.senseless.androidutilcode.databinding.FragmentVideoBinding
import cn.senseless.scaffold.base.ScaffoldFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class VideoFragment : ScaffoldFragment<FragmentVideoBinding>(), Player.Listener {
    private var player: ExoPlayer? = null
    private var video: Video? = null
    private val model by activityViewModels<VideoViewModel>()

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        video = arguments?.getParcelable("video")
        binding.data = video

        player = model.obtainPlayer(requireContext()).also {
            it.addListener(this)
            it.setVideoTextureView(binding.videoView)
            it.setMediaItem(MediaItem.fromUri(video!!.path!!))
            it.prepare()
        }

        liveData {
            while (coroutineContext.isActive) {
                val position = player?.currentPosition ?: 0
                emit((position / 1000).toInt())
                delay(1000)
            }
        }.observe(viewLifecycleOwner) {
            binding.progressBar.max = ((player?.duration ?: 0L) / 1000).toInt()
            binding.progressBar.progress = it
        }
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onResume() {
        super.onResume()
        player?.let {
            if (it.playbackState == Player.STATE_READY) {
                it.play()
            } else if (!it.playWhenReady) {
                it.playWhenReady = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.removeListener(this)
        model.recyclePlayer(player)
        player = null
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        Log.d(TAG, "onVideoSizeChanged: width = ${videoSize.width} ,height = ${videoSize.height}")
        binding.videoView.updateLayoutParams {
            height =
                (resources.displayMetrics.widthPixels * videoSize.pixelWidthHeightRatio).roundToInt()
        }
    }

    companion object {
        private const val TAG = "VideoFragment"
    }
}