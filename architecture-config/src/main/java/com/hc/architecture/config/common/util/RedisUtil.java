package com.hc.architecture.config.common.util;

import com.hc.architecture.config.common.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: hechuan
 * @Date: 2020/5/19 11:46
 * @Description: 封装redis工具
 */
@Component
public class RedisUtil {

    private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private final static long LOCK_EX_TIME = 3000;
    private final static String LOCK_PREFIX = "lock:";

    private final static long EXPIRETIME = 6000;

    //定义释放锁的lua脚本 get key的value和传入的KEYS[2](加锁的value)比较 等直接删除key，lua保证命令原子性
    private final static DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            "if redis.call(\"get\",KEYS[1]) == KEYS[2] then return redis.call(\"del\",KEYS[1]) else return -1 end"
            , Long.class
    );
    @Autowired
    private RedisTemplate<String, Object> redisTemplate; //redis template

    /**
     * 设置某个key过期时间
     *
     * @param key  键
     * @param time 过去时间 秒
     * @return
     */
    public boolean expireTime(String key, long time) {
        try {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("设置键%s过期时间为%d异常", key, time));
        }
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value, EXPIRETIME, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("设置key=%s值value=%s到缓存异常", key, value));
        }
    }


    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object get(String key) {

        try {
            if (key == null) return null;
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("获取缓存异常，key=%s", key));
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean delKey(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("删除缓存异常，key=%s", key));
        }

    }


    /**
     * 设置hash缓存
     *
     * @param key   * @param field hash字段
     * @param value hash值
     * @return
     */
    public boolean hset(String key, String field, String value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            //设置过期时间
            expireTime(key, EXPIRETIME);
            return true;
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("设置hash值key=%s字段 field=%s值value=%s异常", key, field, value));
        }
    }

    /**
     * 获取hash缓存
     *
     * @param key * @param field hash字段
     * @return
     */
    public Object hget(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);

        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("获取hash值异常key=%s", key));
        }
    }

    /**
     * 获取hash缓存
     *
     * @param key * @param field hash字段
     * @return
     */
    public Object hdet(String key) {
        try {
            return redisTemplate.delete(key);

        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("删除hash值异常key=%s", key));
        }
    }

    /**
     * Redis 2.6.12+ 支持set kv ex nx
     *
     * @param key   锁主键
     * @param value 标识谁加锁
     * @return
     */
    public boolean lock(String key, String value) {
        key = LOCK_PREFIX + key;
        return redisTemplate.opsForValue().setIfAbsent(key, value, LOCK_EX_TIME, TimeUnit.SECONDS);
    }


    /**
     * 解锁：运用lua脚本解锁，如果使用del解锁需要先get
     * 再判断锁是不是自己加的，这样存在问题get判断之后线程挂了
     * 那锁还在的，所以直接走脚本。
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        key = LOCK_PREFIX + key;
        List<String> keys = new ArrayList<>(2);
        keys.add(key);
        keys.add(value);

        try {
            long result = (long) redisTemplate.execute(UNLOCK_LUA_SCRIPT, keys);
            logger.info("redis解锁结果{},解锁key={}", result, key);
            if (result > 0) {

                return true;
            }
        } catch (Exception e) {
            throw new UtilException(e, "REDIS_ERROR", String.format("解锁异常key=%s,value=%s", key, value));
        }

        return false;
    }


}
