package com.bear.house.redis.service.impl;

import com.bear.house.redis.service.JedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author WangTao
 *         Created at 18/6/20 下午7:47.
 */
@Service
public class JedisTestServiceImpl implements JedisTestService {

    @Autowired
    private Jedis jedisClient;

    @Override
    public void test() {
        jedisClient.set("a", "wangtao");
        System.out.println(jedisClient.get("a"));
    }

    @Override
    public void setString(String value) {
        jedisClient.set("name", value);
    }

    @Override
    public String getString(String key) {
        return jedisClient.get(key);
    }
}
