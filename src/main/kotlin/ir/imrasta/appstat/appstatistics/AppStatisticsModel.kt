package ir.imrasta.appstat.appstatistics

import java.io.Serializable

data class AppStatisticsModel(val year: Int, val week: Int,
    val requests : Int, val clicks : Int, val installs : Int) : Comparable<AppStatisticsModel>, Serializable{


    override fun compareTo(other: AppStatisticsModel): Int {
        var result = year.compareTo(other.year)
        if (result == 0) result = week.compareTo(other.week)
        return result
    }
}


