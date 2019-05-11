package com.yhh.ssmblog.shiro;

import com.yhh.ssmblog.redis.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<K,V> implements Cache<K, V> {

    @Autowired
    private JedisUtil jedisUtill;

    private final String CACHE_PREFIX = "cache:";

    private byte[] getKey(K k){
        if(k instanceof  String)
            return (CACHE_PREFIX+k).getBytes();
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K k) throws CacheException {
        byte[] value = jedisUtill.get(getKey(k));
        if(value!=null)
            return (V) SerializationUtils.deserialize(value);
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtill.set(key,value);
        jedisUtill.expire(key,600);

        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtill.get(key);
        jedisUtill.del(key);
        if(value!=null)
            return (V) SerializationUtils.deserialize(value);

        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
