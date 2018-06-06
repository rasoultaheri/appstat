package ir.imrasta.appstat.appstatistics

import ir.imrasta.util.DateUtility
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
class AppStatisticsServiceTest {

    @InjectMocks
    lateinit var appStatisticsService : AppStatisticsService

    @MockBean
    lateinit var appStatisticsRepository : AppStatisticsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetStatsReport_singleValue() {
        val from = DateUtility.jalaliToGregorian(1395, 1, 10)
        val to = DateUtility.jalaliToGregorian(1395, 1, 12)

        val stat_10 = AppStatistics(1, DateUtility.jalaliToGregorian(1395,1,10), 10,10,10,10,10,10)
        val stat_20 = AppStatistics(1, DateUtility.jalaliToGregorian(1395,1,11), 20,20,20,20,20,20)
        `when`(appStatisticsRepository.findByTypeAndReportTimeBetween(1, from, to)).thenReturn(listOf(stat_10, stat_20))

        val model = AppStatisticsModel(1395, 2, 60, 60 , 60)
        assertEquals(listOf(model), appStatisticsService.getStatsReport(1, from, to))
    }


    @Test
    fun testGetStatsReport_emptyResult() {
        val from = DateUtility.jalaliToGregorian(1395, 1, 10)
        val to = DateUtility.jalaliToGregorian(1395, 12, 12)

        `when`(appStatisticsRepository.findByTypeAndReportTimeBetween(1,  from, to)).thenReturn(listOf())
        assertTrue(appStatisticsService.getStatsReport(1, from, to).isEmpty())
    }


    @Test
    fun testGetStatsReport_multiResultAndSort() {
        val from = DateUtility.jalaliToGregorian(1395, 1, 10)
        val to = DateUtility.jalaliToGregorian(1395, 10, 12)

        val stat_10 = AppStatistics(1, DateUtility.jalaliToGregorian(1395,1,10), 10,10,10,10,10,10)
        val stat_20 = AppStatistics(1, DateUtility.jalaliToGregorian(1395,1,11), 20,20,20,20,20,20)
        val stat_40 = AppStatistics(1, DateUtility.jalaliToGregorian(1395,2,10), 40,40,40,40,40,40)
        `when`(appStatisticsRepository.findByTypeAndReportTimeBetween(1, from, to)).thenReturn(listOf(stat_10, stat_20, stat_40))

        val model_60 = AppStatisticsModel(1395, 2, 60, 60 , 60)
        val model_80 = AppStatisticsModel(1395, 6, 80, 80 , 80)
        assertEquals(listOf(model_60, model_80), appStatisticsService.getStatsReport(1, from, to))
        assertNotEquals(listOf(model_80, model_60), appStatisticsService.getStatsReport(1, from, to))
    }

}