package ir.imrasta.appstat.appstatistics

import ir.imrasta.util.DateUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AppStatisticsController {

    @Autowired
    lateinit var appStatisticsService: AppStatisticsService

    @GetMapping("/stats")
    fun getStats(@RequestParam type: Int,
                 @RequestParam(defaultValue = "2016-03-20") @DateTimeFormat(pattern = "yyyy-MM-dd") startDate : Date ,
                 @DateTimeFormat(pattern = "yyyy-MM-dd") endDate : Date? ): List<AppStatisticsModel> {
        val from = DateUtility.getDateWithoutTime(startDate)
        val to = DateUtility.getMidNightTime(endDate ?: Date())

        return appStatisticsService.getStatsReport(type, from, to)
    }

}