package cn.senseless.scaffold

import cn.senseless.scaffold.utils.DateUtils
import cn.senseless.scaffold.utils.toBoolean
import cn.senseless.scaffold.utils.toInt
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun dimension() {
        assertEquals(0.toBoolean(), false)
        assertEquals(1.toBoolean(), true)
        assertEquals(0, false.toInt())
        assertEquals(1, true.toInt())
    }

    @Test
    fun date_secondToHms() {
        assertEquals(DateUtils.secondsToHms(0), "0:0:0")
        assertEquals(DateUtils.secondsToHms(1), "0:0:1")
        assertEquals(DateUtils.secondsToHms(60), "0:1:0")
        assertEquals(DateUtils.secondsToHms(61), "0:1:1")
        assertEquals(DateUtils.secondsToHms(71), "0:1:11")
        assertEquals(DateUtils.secondsToHms(120), "0:2:0")
        assertEquals(DateUtils.secondsToHms(86399), "23:59:59")
    }

    @Test
    fun date_secondToHms2() {
        assertEquals(DateUtils.secondsToHms(0, true), "00:00:00")
        assertEquals(DateUtils.secondsToHms(1, true), "00:00:01")
        assertEquals(DateUtils.secondsToHms(60, true), "00:01:00")
        assertEquals(DateUtils.secondsToHms(61, true), "00:01:01")
        assertEquals(DateUtils.secondsToHms(71, true), "00:01:11")
        assertEquals(DateUtils.secondsToHms(120, true), "00:02:00")
        assertEquals(DateUtils.secondsToHms(86399, true), "23:59:59")
    }
}