package ir.imrasta.appstat.appstatistics

import java.io.Serializable

data class AppStatisticsModel(val year: Int, val week: Int) : Comparable<AppStatisticsModel>, Serializable{

    var requests : Int = 0
    var clicks: Int = 0
    var installs: Int = 0

    constructor( year: Int, week: Int, requests : Int, clicks : Int, installs : Int) : this (year, week) {
        this.requests = requests
        this.clicks = clicks
        this.installs = installs
    }

    override fun compareTo(other: AppStatisticsModel): Int {
        var result = year.compareTo(other.year)
        if (result == 0) result = week.compareTo(other.week)
        return result
    }
}


//data class AppStatisticsModel(val year: Int, val week: Int,
//                              val requests : Int, val clicks : Int, val installs : Int)
//    : Comparable<AppStatisticsModel>, Serializable{
//
//
//    override fun compareTo(other: AppStatisticsModel): Int {
//        var result = year.compareTo(other.year)
//        if (result == 0) result = week.compareTo(other.week)
//        return result
//    }
//
//}