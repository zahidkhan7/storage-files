package com.zad.excellence.idp.wellknown.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwks")
public class JwksProperties {

    private String cacheKey;
    private long cacheTtlMillis;

    public String getCacheKey() { return cacheKey; }
    public void setCacheKey(String cacheKey) { this.cacheKey = cacheKey; }

    public long getCacheTtlMillis() { return cacheTtlMillis; }
    public void setCacheTtlMillis(long cacheTtlMillis) { this.cacheTtlMillis = cacheTtlMillis; }
}
