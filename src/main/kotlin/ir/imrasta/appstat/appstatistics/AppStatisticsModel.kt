package ir.imrasta.appstat.appstatistics

import java.io.Serializable

data class AppStatisticsModel(val year: Int, val week: Int) : Comparable<AppStatisticsModel>, Serializable{

    var requests : Int = 0
    var clicks: Int = 0
    var installs: Int = 0


    fun add(appStat : AppStatistics) {
        this.requests += appStat.getRequests()
        this.clicks += appStat.getClicks()
        this.installs += appStat.getInstalls()
    }

    override fun compareTo(other: AppStatisticsModel): Int {
        var result = year.compareTo(other.year)
        if (result == 0) result = week.compareTo(other.week)
        return result
    }

}