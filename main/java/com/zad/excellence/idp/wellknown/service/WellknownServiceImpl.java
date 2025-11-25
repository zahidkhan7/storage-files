package com.zad.excellence.idp.wellknown.service;

import com.zad.excellence.idp.core.service.CoreService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zad.excellence.idp.wellknown.config.OpenIdProperties;
import com.zad.excellence.idp.wellknown.config.JwksProperties;

import com.zad.excellence.idp.core.cache.LocalMemoryAtomicCacheStore;
import com.zad.excellence.idp.core.cache.CacheStore;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


@Service
public class WellknownServiceImpl implements WellknownService , CoreService<Map<String, Object>> {


    private final OpenIdProperties openIdProperties;
    private final JwksProperties jwksProperties;

    private final CacheStore<String, Map<String, Object>> jwksLocalMemoryCache = new LocalMemoryAtomicCacheStore<>();

    public WellknownServiceImpl(OpenIdProperties openIdProperties, JwksProperties jwksProperties) {
        this.openIdProperties = openIdProperties;
        this.jwksProperties = jwksProperties;
    }

    @Override
    public Map<String, Object> getJwksKeys() {
        String cacheKey = jwksProperties.getCacheKey();
        long ttlMillis = jwksProperties.getCacheTtlMillis();

        if (jwksLocalMemoryCache.containsCacheData(cacheKey)) {
            return jwksLocalMemoryCache.getCacheData(cacheKey);
        }

        Map<String, Object> jwks =  getDataFromSource();
        jwksLocalMemoryCache.putCacheData(cacheKey, jwks, ttlMillis);

        return jwks;
    }


    @Override
    public Map<String, Object> getOpenIdConfiguration() {

        Map<String, Object> config = new HashMap<>();
        config.put("issuer", openIdProperties.getIssuer());

        config.put("authorization_endpoint", openIdProperties.getAuthorizationEndpoint());
        config.put("token_endpoint", openIdProperties.getTokenEndpoint());
        config.put("userinfo_endpoint", openIdProperties.getUserInfoEndpoint());
        config.put("introspection_endpoint", openIdProperties.getIntrospectionEndpoint());
        config.put("revocation_endpoint", openIdProperties.getRevocationEndpoint());
        config.put("end_session_endpoint", openIdProperties.getEndSessionEndpoint());

        config.put("response_types_supported", openIdProperties.getResponseTypesSupported());
        config.put("subject_types_supported", openIdProperties.getSubjectTypesSupported());
        config.put("grant_types_supported", openIdProperties.getGrantTypesSupported());
        config.put("claims_supported", openIdProperties.getClaimsSupported());
        config.put("scopes_supported", openIdProperties.getScopesSupported());
        config.put("token_endpoint_auth_methods_supported", openIdProperties.getTokenEndpointAuthMethodsSupported());
        config.put("id_token_signing_alg_values_supported", openIdProperties.getIdTokenSigningAlgValuesSupported());

        return config;
    }


    private Map<String, Object> generateMultipleJwks() {
        try {
            Map<String, Object> jwk1 = generateRsaJwk();
            Map<String, Object> jwk2 = generateRsaJwk();
            Map<String, Object> jwk3 = generateRsaJwk();

            Map<String, Object> jwks = new HashMap<>();
            jwks.put("keys", List.of(jwk1, jwk2, jwk3));
            return jwks;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate JWKS keys", ex);
        }
    }

    private Map<String, Object> generateRsaJwk() throws Exception {
        // Create 2048-bit RSA Key Pair
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // Create modulus (n) and exponent (e)
        String n = base64Url(publicKey.getModulus());
        String e = base64Url(publicKey.getPublicExponent());

        // Unique Key ID
        String kid = Integer.toHexString(publicKey.hashCode());

        Map<String, Object> jwk = new HashMap<>();
        jwk.put("kty", "RSA");
        jwk.put("use", "sig");
        jwk.put("alg", "RS256");
        jwk.put("kid", kid);
        jwk.put("n", n);
        jwk.put("e", e);

        return jwk;
    }

    private String base64Url(BigInteger value) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.toByteArray());
    }

    @Override
    public Map<String, Object> getDataFromSource() {
        return generateMultipleJwks();
    }

}
