package com.radel.core.radeljwksaggregatorservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radel.core.radeljwksaggregatorservice.JwksService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jwks")
@RequiredArgsConstructor
class JwksController {

    private final JwksService jwksService;

    @GetMapping
    Object getJwks() {
        return jwksService.getJwks().toJSONObject();
    }
}
