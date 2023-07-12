package cn.senseless.scaffold.utils

import android.widget.SeekBar


inline fun SeekBar.setOnSeekBarChangeListener(
    crossinline onProgressChanged: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit = { _, _, _ -> },
    crossinline onStartTrackingTouch: (seekBar: SeekBar) -> Unit = {},
    crossinline onStopTrackingTouch: (seekBar: SeekBar) -> Unit = {}
): SeekBar.OnSeekBarChangeListener {
    val listener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onProgressChanged(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            onStartTrackingTouch(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            onStopTrackingTouch(seekBar)
        }
    }
    setOnSeekBarChangeListener(listener)
    return listener
}

inline fun SeekBar.doOnProgressChanged(
    crossinline action: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit
) = setOnSeekBarChangeListener(onProgressChanged = action)

inline fun SeekBar.doOnStartTrackingTouch(
    crossinline action: (seekBar: SeekBar) -> Unit
) = setOnSeekBarChangeListener(onStartTrackingTouch = action)

inline fun SeekBar.doOnStopTrackingTouch(
    crossinline action: (seekBar: SeekBar) -> Unit
) = setOnSeekBarChangeListener(onStopTrackingTouch = action)