//package com.ky.backtracking.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisUtil {
//
////    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
////    @Autowired
////    private StringRedisTemplate stringRedisTemplate;
//
//    public void put(Object key, Object value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    public void putWithTimeout(Object key, Object value, long timeout) {
//        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
//    }
//
//    public Object get(Object key) {
//        if (null == key) {
//            return null;
//        } else {
//            return redisTemplate.opsForValue().get(key);
//        }
//    }
//
//    public boolean delete(Object key) {
//        if (null == key) {
//            return true;
//        } else {
//            return redisTemplate.delete(key);
//        }
//    }
//}
