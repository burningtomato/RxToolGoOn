package com.bt.dialog.util

import java.util.*

/**
 * Author by firetomato, Github:{@link <a href="https://github.com/burningtomato">...</a>}, Date on 2022/11/15.
 * PS: Not easy to write code, please indicate.
 */
object RxTimeTool {
    /**
     * 根据年 月 获取对应的月份 天数
     */
    @JvmStatic
    fun getDaysByYearMonth(year: Int, month: Int): Int {
        val a = Calendar.getInstance()
        a[Calendar.YEAR] = year
        a[Calendar.MONTH] = month - 1
        a[Calendar.DATE] = 1
        a.roll(Calendar.DATE, -1)
        return a[Calendar.DATE]
    }
}