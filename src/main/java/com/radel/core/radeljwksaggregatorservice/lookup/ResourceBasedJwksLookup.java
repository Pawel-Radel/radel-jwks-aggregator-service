package com.radel.core.radeljwksaggregatorservice.lookup;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.nimbusds.jose.jwk.JWKSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ResourceBasedJwksLookup implements JwksLookup {

    private final Resource resource;

    private JWKSet jwkSet;

    @Override
    public JWKSet lookup() {
        return jwkSet;
    }

    public void init() {

        try (InputStream in = resource.getInputStream()) {
            jwkSet = JWKSet.parse(StreamUtils.copyToString(in, StandardCharsets.UTF_8));
        } catch (IOException | ParseException e) {
            log.warn("Could not load JWKS from resource. resource={} error={}", resource.getFilename(), e.getMessage());
            jwkSet = null;
        }
    }

    @Override
    public String toString() {
        return "ResourceBasedJwksLookup{" +
                "resource=" + resource.getFilename() +
                '}';
    }
}
