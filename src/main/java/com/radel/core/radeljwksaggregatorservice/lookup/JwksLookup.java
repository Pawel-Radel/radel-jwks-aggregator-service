package com.radel.core.radeljwksaggregatorservice.lookup;

import com.nimbusds.jose.jwk.JWKSet;

public interface JwksLookup {

    JWKSet lookup();
}
