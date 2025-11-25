package com.zad.excellence.idp.wellknown.service;

import java.util.Map;

public interface WellknownService {
	
	 /**
     * Returns OpenID Connect configuration details.
     */
    Map<String, Object> getOpenIdConfiguration();

    /**
     * Returns JWKS keys.
     */
    Map<String, Object> getJwksKeys();
}
