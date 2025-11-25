package com.zad.excellence.idp.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import com.zad.excellence.idp.core.model.CacheEntry;

/**
 * Thread-safe local memory cache using ConcurrentHashMap and AtomicReference.
 * TTL-based expiration per entry, fully enterprise-ready.
 */
public class LocalMemoryAtomicCacheStore<K, V> implements CacheStore<K, V> {

    private final Map<K, AtomicReference<CacheEntry<V>>> localCacheStore = new ConcurrentHashMap<>();

    @Override
    public V getCacheData(K key) {
        AtomicReference<CacheEntry<V>> ref = localCacheStore.get(key);
        if (ref == null) return null;

        CacheEntry<V> entry = ref.get();
        if (entry == null || entry.isExpired()) {
            localCacheStore.remove(key);
            return null;
        }

        return entry.getValue();
    }

    @Override
    public void putCacheData(K key, V value, long ttlMillis) {
        CacheEntry<V> newEntry = new CacheEntry<>(value, ttlMillis);
        localCacheStore.compute(key, (k, oldRef) -> {
            if (oldRef == null) {
                return new AtomicReference<>(newEntry);
            } else {
                oldRef.set(newEntry);
                return oldRef;
            }
        });
    }

    @Override
    public boolean containsCacheData(K key) {
        AtomicReference<CacheEntry<V>> ref = localCacheStore.get(key);
        if (ref == null) return false;

        CacheEntry<V> entry = ref.get();
        if (entry == null || entry.isExpired()) {
            localCacheStore.remove(key);
            return false;
        }

        return true;
    }

    @Override
    public void evictCacheData(K key) {
        localCacheStore.remove(key);
    }

    @Override
    public void clearCacheData() {
        localCacheStore.clear();
    }

    /**
     * Optional helper: get current cache size (removes expired entries first)
     */
    public int size() {
        localCacheStore.entrySet().removeIf(e -> {
            AtomicReference<CacheEntry<V>> ref = e.getValue();
            return ref.get() == null || ref.get().isExpired();
        });
        return localCacheStore.size();
    }
}

