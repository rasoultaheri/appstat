package ir.imrasta.appstat.appstatistics

import ir.imrasta.appstat.util.DateUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AppStatisticsService {

    @Autowired
    lateinit var appStatisticsRepository : AppStatisticsRepository


    @GetMapping("/stats")
    @Cacheable(value = "stats", key = "{#type,#startDate,#endDate}")
    fun getStats(@RequestParam type: Int,
                 @RequestParam(defaultValue = "2016-03-20") @DateTimeFormat(pattern = "yyyy-MM-dd") startDate : Date ,
                 @DateTimeFormat(pattern = "yyyy-MM-dd") endDate : Date? ): List<AppStatisticsModel> {

        return groupingAndSort(appStatisticsRepository.findByTypeAndReportTimeBetween(type, startDate, DateUtility.getMidNightTime(endDate ?: Date())));
    }

    private fun groupingAndSort(stats: List<AppStatistics>) : List<AppStatisticsModel> {
        val grouped = mutableListOf<AppStatisticsModel>();
        stats.groupBy { Pair(it.getPersianYear(), it.getPersianWeek()) }
             .mapValues {Triple(it.value.sumBy { it.getRequests() },it.value.sumBy { it.getClicks() },it.value.sumBy { it.getInstalls() })}
             .forEach { k, v -> grouped.add(AppStatisticsModel(k.first, k.second, v.first, v.second, v.third)) }

        return grouped.sorted();
    }

}