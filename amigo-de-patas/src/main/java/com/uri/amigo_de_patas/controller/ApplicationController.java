package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.ApplicationDTO;
import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.model.ApplicationStatus;
import com.uri.amigo_de_patas.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Application>> listAll() {
        return ResponseEntity.ok(applicationService.findAllApplications());
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody ApplicationDTO dto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Application application = applicationService.createApplication(dto, userDetails.getUsername());
        return ResponseEntity.ok(application);
    }

    @PutMapping("{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveApplication(@PathVariable UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.ACEITO);
        return ResponseEntity.ok(Map.of("message", "candidatura aprovada"));
    }

    @PutMapping("{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectApplication(@PathVariable UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.RECUSADO);
        return ResponseEntity.ok(Map.of("message", "candidatura recusada"));
    }

}

