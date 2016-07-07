package com.tsingda.simple.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching(mode = AdviceMode.PROXY)
public class CacheConfig extends CachingConfigurerSupport {

    //缓存默认有效时间
    private static final long DEFAULT_CACHE_EXPIRATION = 600l;

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

    @Bean(name = "redisCacheConnectionFactory")
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
        //生产环境去掉，测试只用一个DB就
        jedisConnectionFactory.setDatabase(redisDatabase);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisCacheTemplate")
    public <K, V> RedisTemplate<K, V> redisCacheTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<K, V>();
        template.setConnectionFactory(connectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }
    
    @Override
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheTemplate());
        cacheManager.setDefaultExpiration(DEFAULT_CACHE_EXPIRATION);
        return cacheManager;
    }
    
    @Override
    public KeyGenerator keyGenerator() {
        SimpleKeyGenerator keyGenerator = new SimpleKeyGenerator();
        return keyGenerator;
    }

}
