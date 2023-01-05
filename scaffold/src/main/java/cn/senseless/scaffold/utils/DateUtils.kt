package cn.senseless.scaffold.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DEFAULT_DATE_FORMAT = "yyyy:MM:dd HH:mm:ss"
    const val DEFAULT_DATE_ONLY_DAY_FORMAT = "yyyy:MM:dd"
    const val DEFAULT_DATE_ONLY_TIME_FORMAT = "HH:mm:ss"

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

    fun dateFormat(date: Date, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.CHINA)
        return simpleDateFormat.format(date)
    }
}