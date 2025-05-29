package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.ApplicationDTO;
import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody ApplicationDTO dto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Application application = applicationService.createApplication(dto, userDetails.getUsername());
        return ResponseEntity.ok(application);
    }
}

