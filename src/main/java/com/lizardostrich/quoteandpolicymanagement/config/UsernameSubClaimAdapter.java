package com.lizardostrich.quoteandpolicymanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {
    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());
    private final Logger logger = LoggerFactory.getLogger(UsernameSubClaimAdapter.class);

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);

        if (claims.containsKey("cognito:groups")) {
            convertedClaims.put("authorities", claims.get("cognito:groups"));
        }
        //PreAuthorise(hasAuthority('') actually uses 'scope' from User
        if (claims.containsKey("scope")) {
            convertedClaims.put("scope", claims.get("cognito:groups"));
        }
        if (claims.containsKey("username")) {
            convertedClaims.put("user_name", claims.get("username"));
        }
        logger.info(claims.toString());
        logger.info("User: "+convertedClaims);
        return convertedClaims;
    }

}