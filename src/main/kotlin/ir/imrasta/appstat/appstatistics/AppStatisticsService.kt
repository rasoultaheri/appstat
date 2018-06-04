package ir.imrasta.appstat.appstatistics

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppStatisticsService {

    @Autowired
    lateinit var appStatisticsRepository: AppStatisticsRepository

    @Cacheable(value = "stats", key = "{#type,#from,#to}")
    fun getStatsReport(type: Int, from: Date, to: Date): List<AppStatisticsModel> {
        val stats = appStatisticsRepository.findByTypeAndReportTimeBetween(type, from, to)

        val grouped = mutableListOf<AppStatisticsModel>();
        stats.groupBy { Pair(it.getPersianYear(), it.getPersianWeek()) }
                .mapValues { Triple(it.value.sumBy { it.getRequests() }, it.value.sumBy { it.getClicks() }, it.value.sumBy { it.getInstalls() }) }
                .forEach { k, v -> grouped.add(AppStatisticsModel(k.first, k.second, v.first, v.second, v.third)) }

        return grouped.sorted();
    }

}