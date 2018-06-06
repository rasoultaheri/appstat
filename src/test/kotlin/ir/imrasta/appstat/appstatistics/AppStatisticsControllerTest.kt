package ir.imrasta.appstat.appstatistics

import com.fasterxml.jackson.databind.ObjectMapper
import ir.imrasta.util.DateUtility
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*


@RunWith(SpringRunner::class)
@WebMvcTest(AppStatisticsController::class)
class AppStatisticsControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockBean
    lateinit var appStatisticsService: AppStatisticsService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var appStatisticsRepository: AppStatisticsRepository

    val from = DateUtility.jalaliToGregorian(1395, 1, 1)
    val to = DateUtility.getMidNightTime(Date())
    val result = listOf(
            AppStatisticsModel(1395, 2, 60, 60 , 60),
            AppStatisticsModel(1395, 6, 80, 80 , 80))

    @Before
    fun setup() {
        given(appStatisticsService.getStatsReport(1, from, to)).willReturn(result)
    }


    @Test
    fun testGetStats() {
        val df = SimpleDateFormat("yyyy-MM-dd")
        mockMvc.perform(MockMvcRequestBuilders.get("/stats?type={type}&startDate={from}&endDate={to}",
                1, df.format(from), df.format(to))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
        .andExpect(content().json(objectMapper.writeValueAsString(result)))
    }

    @Test
    fun testGetStats_endDateIsOptional() {
        val df = SimpleDateFormat("yyyy-MM-dd")
        mockMvc.perform(MockMvcRequestBuilders.get("/stats?type={type}&startDate={from}",
                1, df.format(from))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andExpect(content().json(objectMapper.writeValueAsString(result)))
    }

    @Test
    fun testGetStats_startDateIsOptional() {
        mockMvc.perform(MockMvcRequestBuilders.get("/stats?type={type}", 1)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andExpect(content().json(objectMapper.writeValueAsString(result)))
    }

    @Test
    fun testGetStats_typeIsRequired() {
        mockMvc.perform(MockMvcRequestBuilders.get("/stats")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().`is`(400))
    }

}