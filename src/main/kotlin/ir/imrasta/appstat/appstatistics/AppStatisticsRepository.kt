package ir.imrasta.appstat.appstatistics

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface AppStatisticsRepository : MongoRepository<AppStatistics, String> {

    fun findByTypeAndReportTimeBetween(type: Int, repotTimeGE: Date, reportTimeLE: Date) : List<AppStatistics>

}