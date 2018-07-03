package com.bear.house.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author WangTao
 *         Created at 18/6/20 下午7:44.
 */
@Configuration
public class JedisConfig {

    @Bean
    public Jedis getJedis() {
        return new Jedis("localhost", 6379);
    }

}
