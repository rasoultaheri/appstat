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

        val modelList = grouping(
                appStatisticsRepository.findByTypeAndReportTimeBetween(type, startDate, DateUtility.getMidNightTime(endDate ?: Date()))
        )
        Collections.sort(modelList)
        return modelList
    }

    private fun grouping(stats: List<AppStatistics>): List<AppStatisticsModel> {
        val map = mutableMapOf<AppStatisticsModel, MutableList<AppStatistics>>()
        stats.forEach({
            val model = AppStatisticsModel(it.getPersianYear(), it.getPersianWeek())
            if (!map.contains(model))
                map.put(model, mutableListOf())

            map.get(model)?.add(it)
        })

        var groups = mutableListOf<AppStatisticsModel>()
        map.forEach({(key, values) ->
            values.forEach { key.add(it) }
            groups.add(key)
        })
        return groups
    }

}