package com.radel.core.radeljwksaggregatorservice.lookup;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import com.nimbusds.jose.jwk.JWKSet;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class DynamicJwksLookup implements JwksLookup {

    private final Duration cacheDuration;

    private final AtomicReference<JwksHolder> reference = new AtomicReference<>(new JwksHolder(null, 0L));

    @Data
    static class JwksHolder {

        private final JWKSet jwks;

        private final long updatedAt;
    }

    @Override
    public JWKSet lookup() {

        JwksHolder jwksHolder = this.reference.get();

        long dt = System.currentTimeMillis() - jwksHolder.getUpdatedAt();
        if (dt < cacheDuration.toMillis()) {
            log.debug("Skipping jwks lookup: cacheDuration. lookup={}", this);
            return jwksHolder.getJwks();
        }

        long fetchStartTimeMillis = System.currentTimeMillis();
        JWKSet jwks = fetch();
        long fetchEndedTimeMillis = System.currentTimeMillis();

        if (jwks == null) {
            log.debug("Skipping empty jwks update. lookup={}", this);
            jwks = jwksHolder.getJwks();
        } else {
            log.info("Updated jwks. lookup={} took {}ms", this, fetchEndedTimeMillis - fetchStartTimeMillis);
        }
        this.reference.compareAndSet(jwksHolder, new JwksHolder(jwks, fetchEndedTimeMillis));

        return jwks;
    }

    public abstract JWKSet fetch();
}
