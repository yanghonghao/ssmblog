package com.yhh.ssmblog.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class JedisUtil {
    @Autowired
    JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try{
            jedis.set(key, value);
            return value;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            jedis.close();
        }
        return null;
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try {
            jedis.expire(key, i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }



    public void del(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((shiro_session_prefix).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }


}
