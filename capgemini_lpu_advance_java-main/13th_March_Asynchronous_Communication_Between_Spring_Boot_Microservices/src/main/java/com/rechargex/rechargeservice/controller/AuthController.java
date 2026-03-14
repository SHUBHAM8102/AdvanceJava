package com.rechargex.rechargeservice.controller;

import com.rechargex.rechargeservice.security.JwtService;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public Map<String, String> token(@RequestParam @Min(1) Long userId, @RequestParam(defaultValue = "USER") String role) {
        return Map.of("token", jwtService.generateToken(userId, role));
    }
}
