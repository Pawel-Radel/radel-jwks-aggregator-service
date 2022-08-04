package com.radel.core.radeljwksaggregatorservice;

import com.nimbusds.jose.jwk.JWKSet;

public interface JwksService {

    JWKSet getJwks();
}
