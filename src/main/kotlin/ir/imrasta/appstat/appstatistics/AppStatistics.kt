package ir.imrasta.appstat.appstatistics

import ir.imrasta.appstat.util.DateUtility
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class AppStatistics  {

    @Id
    var id : String? = null
    var type : Int = 0
    var reportTime : Date = Date()
    var videoRequests : Int = 0
    var webViewRequests : Int = 0
    var videoClicks : Int = 0
    var webViewClicks : Int = 0
    var videoInstalls : Int = 0
    var webViewInstalls : Int = 0


    constructor(type : Int, reportTime : Date,
                videoRequests : Int, webViewRequests : Int,
                videoClicks : Int, webViewClicks : Int,
                videoInstalls : Int, webViewInstalls : Int) {
        this.type = type
        this.reportTime = reportTime
        this.videoRequests = videoRequests
        this.webViewRequests = webViewRequests
        this.videoClicks = videoClicks
        this.webViewClicks = webViewClicks
        this.videoInstalls = videoInstalls
        this.webViewInstalls = webViewInstalls
    }

    fun getRequests() = videoRequests + webViewRequests
    fun getClicks() = videoClicks + webViewClicks
    fun getInstalls() = videoInstalls + webViewInstalls

    fun getPersianYear() = DateUtility.getJalaliYearOf(reportTime!!)
    fun getPersianWeek() = DateUtility.getJalaliMonthOf(reportTime!!)

}