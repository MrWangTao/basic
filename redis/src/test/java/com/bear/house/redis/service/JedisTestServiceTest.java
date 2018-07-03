package com.bear.house.redis.service;

import com.bear.house.redis.RedisApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author WangTao
 *         Created at 18/6/20 下午7:50.
 */
public class JedisTestServiceTest extends RedisApplicationTests {

    @Autowired
    private JedisTestService jedisTestService;

    @Test
    public void test1() throws Exception {
        jedisTestService.test();
    }

}