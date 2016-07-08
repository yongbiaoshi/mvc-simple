package com.tsingda.simple.config;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.tsingda.simple.util.JsonUtil;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.password}")
    private String redisPassword;
    @Value("${redis.database}")
    private int redisDatabase;
    @Value("${redis.maxWaitMillis}")
    private long redisMaxWaitMillis;
    
    @PostConstruct
    public void init(){
    }

    /**
     * redis connection factory config
     *
     * @return redis connection factory
     */
    @Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(redisPort);
        jedisConnectionFactory.setPassword(redisPassword);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(redisMaxWaitMillis);
        poolConfig.setMaxTotal(100);
        jedisConnectionFactory.setPoolConfig(poolConfig);
        // 生产环境去掉，测试只用一个DB就
        jedisConnectionFactory.setDatabase(redisDatabase);
        return jedisConnectionFactory;
    }

    /**
     * redis template
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory,
            StringRedisSerializer stringRedisSerializer,
            GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {
        RedisTemplate<K, V> template = new RedisTemplate<K, V>();
        template.setConnectionFactory(connectionFactory);

        template.setDefaultSerializer(stringRedisSerializer);
        template.setStringSerializer(stringRedisSerializer);
        template.setEnableDefaultSerializer(true);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        // 允许事务（mutlti）
        template.setEnableTransactionSupport(true);

        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory());
        // 允许事务（mutlti）
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer(Charset.forName("UTF-8"));
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(JsonUtil.objectMapper);
        return serializer;
    }
}
