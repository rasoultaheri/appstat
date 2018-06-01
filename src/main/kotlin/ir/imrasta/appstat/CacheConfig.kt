package ir.imrasta.appstat

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.util.*

@Configuration
@EnableCaching
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)
class CacheConfig : CachingConfigurerSupport() {

    @Value(value = "\${spring.redis.host}")
    private var redisHost: String? = null
    @Value("\${spring.redis.port}")
    private var redisPort: Int? = null
    @Value("\${me.redis.ttl}")
    private var cacheTTL: Long? = null

    @Bean
    fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
        return PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val factory = JedisConnectionFactory()
        val prop = prop();
        factory.hostName = redisHost ?: prop.getProperty("spring.redis.host")
        factory.port = redisPort ?: prop.getProperty("spring.redis.port").toInt()
        factory.usePool = true
        return factory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.setConnectionFactory(jedisConnectionFactory())
        return redisTemplate
    }

    @Bean
    override fun cacheManager(): CacheManager {
        val cacheManager = RedisCacheManager(redisTemplate())
        cacheManager.setDefaultExpiration(cacheTTL ?: prop().getProperty("me.redis.ttl").toLong())
        return cacheManager
    }

    fun prop() : Properties {
        val resourceName = "application.properties" // could also be a constant
        val loader = Thread.currentThread().contextClassLoader
        val props = Properties()
        loader.getResourceAsStream(resourceName).use { resourceStream -> props.load(resourceStream) }
        return props
    }

}