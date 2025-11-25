package com.zad.excellence.idp.core.cache;

/**
 * Generic cache interface with standardized method names.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public interface CacheStore<K, V> {

    V getCacheData(K key);

    void putCacheData(K key, V value, long ttlMillis);

    boolean containsCacheData(K key);

    void evictCacheData(K key);

    void clearCacheData();
}



