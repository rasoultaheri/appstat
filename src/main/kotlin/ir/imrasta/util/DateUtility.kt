package ir.imrasta.util

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*


class DateUtility {

    companion object {
        fun getDateWithoutTime(date : Date) : Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }

        fun getMidNightTime(date : Date) : Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            return calendar.time
        }

        fun jalaliToGregorian(year:Int, month: Int, day:Int) : Date {
            val calendar = Calendar.getInstance(ULocale("@calendar=persian"))
            calendar.set(year, month -1, day)
            return getDateWithoutTime(calendar.time)
        }

        fun getJalaliYearOf(date: Date) : Int {
            val cal = Calendar.getInstance(ULocale("@calendar=persian"))
            cal.time = date
            return cal.get(Calendar.YEAR)
        }

        fun getJalaliMonthOf(date: Date) : Int {
            val cal = Calendar.getInstance(ULocale("@calendar=persian"))
            cal.time = date
            return cal.get(Calendar.WEEK_OF_YEAR)
        }
    }

}