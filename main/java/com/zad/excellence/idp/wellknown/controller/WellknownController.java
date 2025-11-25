package com.zad.excellence.idp.wellknown.controller;

import com.zad.excellence.idp.wellknown.service.WellknownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/.well-known")
public class WellknownController {

    private final WellknownService wellknownService;

    public WellknownController(WellknownService wellknownService) {
        this.wellknownService = wellknownService;
    }

    @GetMapping("/openid-configuration")
    public ResponseEntity<Map<String, Object>> getOpenIdConfig() {
        return ResponseEntity.ok(wellknownService.getOpenIdConfiguration());
    }

    @GetMapping("/jwks.json")
    public ResponseEntity<Map<String, Object>> getJwks() {
        return ResponseEntity.ok(wellknownService.getJwksKeys());
    }
}
