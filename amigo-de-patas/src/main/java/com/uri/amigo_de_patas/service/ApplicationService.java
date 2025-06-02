package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.dto.ApplicationDTO;
import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.model.ApplicationStatus;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final AnimalService animalService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserService userService,
                              AnimalService animalService) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.animalService = animalService;
    }

    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    public Application createApplication(ApplicationDTO dto, String username) {
        User user = userService.findByEmail(username);
        Animal animal = animalService.findById(dto.getAnimalId());

        Application application = new Application();
        application.setUser(user);
        application.setAnimal(animal);
        application.setType(dto.getType());
        application.setMessage(dto.getMessage());
        application.setStatus(ApplicationStatus.PENDENTE);

        application.setUserName(user.getNome());
        application.setAddress(user.getEndereco());
        application.setPhone(user.getTelefone());

        return applicationRepository.save(application);
    }

    public void updateStatus(UUID id, ApplicationStatus status) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("candidatura não encontrada"));
        app.setStatus(status);
        applicationRepository.save(app);
    }

}
