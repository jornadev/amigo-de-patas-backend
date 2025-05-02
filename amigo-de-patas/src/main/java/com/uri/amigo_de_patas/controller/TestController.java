package com.uri.amigo_de_patas.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/protected-test")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getProtectedResource() {
        return "acesso autorizado!";
    }

}
