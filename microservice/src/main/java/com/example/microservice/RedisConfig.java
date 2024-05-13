 package com.example.microservice;

 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.cache.annotation.EnableCaching;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.data.redis.cache.RedisCacheConfiguration;
 import org.springframework.data.redis.cache.RedisCacheManager;
 import org.springframework.data.redis.connection.RedisConnectionFactory;
 import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
 import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
 import org.springframework.data.redis.serializer.RedisSerializationContext;
 import org.springframework.data.redis.serializer.StringRedisSerializer;

 import java.time.Duration;

 @Configuration
 @EnableCaching
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(6379);
        return new JedisConnectionFactory(redisConfig);
    }

   @Bean
    public RedisTemplate<String, Object> redisTemplate  () {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

       StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
       redisTemplate.setKeySerializer(stringRedisSerializer);
       redisTemplate.setHashKeySerializer(stringRedisSerializer);

       Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
       redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
       redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

     @Bean
     public RedisCacheConfiguration cacheConfiguration() {
         return RedisCacheConfiguration.defaultCacheConfig()
                 .entryTtl(Duration.ofSeconds(600))
                 .disableCachingNullValues()
                 .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                 .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));
     }

     @Bean
     public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisCacheConfiguration cacheConfiguration) {
         return RedisCacheManager.builder(connectionFactory)
                 .cacheDefaults(cacheConfiguration)
                 .build();
     }

}
