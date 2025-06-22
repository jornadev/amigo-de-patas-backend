package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.ApplicationDTO;
import com.uri.amigo_de_patas.dto.ApplicationResponseDTO;
import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.model.ApplicationStatus;
import com.uri.amigo_de_patas.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
@Tag(name = "Candidaturas", description = "Gerenciamento de candidaturas para adoção")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar candidaturas", description = "Retorna todas as candidaturas (requer papel ADMIN).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de candidaturas retornada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer ADMIN")
    })
    public ResponseEntity<List<Application>> listAll() {
        return ResponseEntity.ok(applicationService.findAllApplications());
    }

    @GetMapping("/my")
    @Operation(summary = "Minhas candidaturas", description = "Lista as candidaturas do usuário autenticado")
    public ResponseEntity<List<ApplicationResponseDTO>> minhasCandidaturas(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<Application> candidaturas = applicationService.findByUserEmail(email);
        List<ApplicationResponseDTO> dtos = candidaturas.stream().map(ApplicationResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/mine")
    @Operation(summary = "Listar minhas candidaturas", description = "Retorna as candidaturas do usuário autenticado.")
    public ResponseEntity<List<Application>> listMyApplications(@AuthenticationPrincipal UserDetails userDetails) {
        List<Application> list = applicationService.findByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir candidatura", description = "Exclui uma candidatura (ADMIN).")
    public ResponseEntity<?> deleteApplication(@PathVariable UUID id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Criar candidatura", description = "Cria uma nova candidatura de adoção para o usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidatura criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public ResponseEntity<Application> create(@RequestBody ApplicationDTO dto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Application application = applicationService.createApplication(dto, userDetails.getUsername());
        return ResponseEntity.ok(application);
    }

    @PutMapping("{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Aprovar candidatura", description = "Altera o status da candidatura para ACEITO (requer papel ADMIN).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidatura aprovada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer ADMIN"),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    public ResponseEntity<?> approveApplication(@PathVariable UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.ACEITO);
        return ResponseEntity.ok(Map.of("message", "candidatura aprovada"));
    }

    @PutMapping("{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Rejeitar candidatura", description = "Altera o status da candidatura para RECUSADO (requer papel ADMIN).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidatura recusada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - requer ADMIN"),
            @ApiResponse(responseCode = "404", description = "Candidatura não encontrada")
    })
    public ResponseEntity<?> rejectApplication(@PathVariable UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.RECUSADO);
        return ResponseEntity.ok(Map.of("message", "candidatura recusada"));
    }
}
