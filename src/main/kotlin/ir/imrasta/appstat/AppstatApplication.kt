package ir.imrasta.appstat

import ir.imrasta.appstat.appstatistics.AppStatistics
import ir.imrasta.appstat.appstatistics.AppStatisticsRepository
import ir.imrasta.appstat.util.DateUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import javax.annotation.PostConstruct

@SpringBootApplication
class AppstatApplication {

    @Autowired
    lateinit var appStatisticsRepository : AppStatisticsRepository

    @PostConstruct
    private fun initData() {
        appStatisticsRepository.deleteAll()
        appStatisticsRepository.save(AppStatistics(1, DateUtility.jalaliToGregorian(1395,1,10), 10,10,10,10,10,10))
        appStatisticsRepository.save(AppStatistics(1, DateUtility.jalaliToGregorian(1396,1,10), 20,20,20,20,20,20))
        appStatisticsRepository.save(AppStatistics(3, DateUtility.jalaliToGregorian(1396,1,10), 30,30,30,30,30,30))
        appStatisticsRepository.save(AppStatistics(2, DateUtility.jalaliToGregorian(1397,2,11), 40,40,40,40,40,40))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(AppstatApplication::class.java, *args)
}
