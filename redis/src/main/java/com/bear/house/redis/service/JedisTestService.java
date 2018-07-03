package com.bear.house.redis.service;

/**
 * @author WangTao
 *         Created at 18/6/20 下午7:47.
 */
public interface JedisTestService {

    void test();

    void setString(String value);

    String getString(String key);


}
