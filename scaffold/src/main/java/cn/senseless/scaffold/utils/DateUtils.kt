package cn.senseless.scaffold.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DEFAULT_DATE_FORMAT = "yyyy:MM:dd HH:mm:ss"
    const val ONLY_DAY_FORMAT = "yyyy:MM:dd"
    const val HMS_FORMAT = "HH:mm:ss"
    const val MS_FORMAT = "mm:ss"

    @JvmStatic
    fun secondsToHms(second: Int, padZero: Boolean = false): String {
        val h = second / 60 / 60 % 24
        val m = second / 60 % 60
        val s = second % 60
        if (padZero) {
            return buildString {
                if (h < 10) {
                    append('0')
                }
                append(h)
                append(':')
                if (m < 10) {
                    append('0')
                }
                append(m)
                append(':')
                if (s < 10) {
                    append('0')
                }
                append(s)
            }
        }
        return "$h:$m:$s"
    }

    @JvmStatic
    fun secondsToMs(second: Int, padZero: Boolean = false): String {
        val m = second / 60 % 60
        val s = second % 60
        if (padZero) {
            return buildString {
                if (m < 10) {
                    append('0')
                }
                append(m)
                append(':')
                if (s < 10) {
                    append('0')
                }
                append(s)
            }
        }
        return "$m:$s"
    }

    @JvmStatic
    fun hmToSecond(date: String): Int {
        return hmToMinute(date) * 60
    }

    @JvmStatic
    fun hmToMinute(date: String): Int {
        if (date.contains(":")) {
            val split = date.split(":")
            if (split.size >= 2) {
                return split[0].toInt() * 60 + split[1].toInt()
            }
        }
        return 0
    }

    @JvmStatic
    fun hmsToMinute(date: String): Int {
        if (date.contains(":")) {
            val split = date.split(":")
            if (split.size >= 3) {
                return split[0].toInt() * 3600 + split[1].toInt() * 60
            }
        }
        return 0
    }

    @JvmStatic
    fun dateFormat(date: Date, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.CHINA)
        return simpleDateFormat.format(date)
    }
}
