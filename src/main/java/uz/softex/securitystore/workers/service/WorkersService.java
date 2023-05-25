package uz.softex.securitystore.workers.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.position.entity.Position;
import uz.softex.securitystore.position.repository.PositionRepository;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.store.exceptions.StoreNotFound;
import uz.softex.securitystore.workers.dto.WorkersChangePassword;
import uz.softex.securitystore.workers.dto.WorkersDto;
import uz.softex.securitystore.workers.dto.WorkersUpdateDto;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.exception.WorkersDtoNotFound;
import uz.softex.securitystore.workers.repository.WorkersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkersService {
     private final
     AuthenticationManager manager;
     private final
      WorkersRepository repository;
     private final
     PositionRepository positionRepository;
     private final
     StoreRepository storeRepository;
     private final
      PasswordEncoder encoder;
     private final
      WorkersRepository workersRepository;

    public WorkersService(AuthenticationManager manager, WorkersRepository repository, PositionRepository positionRepository, StoreRepository storeRepository, PasswordEncoder encoder, WorkersRepository workersRepository) {
        this.manager = manager;
        this.repository = repository;
        this.positionRepository = positionRepository;
        this.storeRepository = storeRepository;
        this.encoder = encoder;
        this.workersRepository = workersRepository;
    }

    public ApiResponseGeneric<?> all() {
        List<Workers> all = repository.findByEnabledIsTrue();
        List<WorkersDto> dtos = new ArrayList<>();
        all.forEach(i ->
                dtos.add(new WorkersDto(i)));
        return new ApiResponseGeneric<>(dtos);
    }

    public ApiResponseGeneric<WorkersDto> getOne(Integer id) {
        return new ApiResponseGeneric<>(new WorkersDto(repository.findById(id).orElseThrow(WorkersDtoNotFound::new)));
    }

    public ApiResponse update(WorkersUpdateDto dto) {
        Workers workers = workersRepository.findByIdAndEnabledIsTrue(dto.getId()).orElseThrow(WorkersDtoNotFound::new);
        Position position = positionRepository.findById(dto.getPosition()).orElseThrow(StoreNotFound::new);
        workers.setSalary(dto.getSalary());
        workers.setFullname(dto.getFullname());
        workers.setUsername(dto.getUsername());
        workers.setPosition(position);
        repository.save(workers);
        return new ApiResponse();
    }

    public ApiResponse delete(Integer id) {
        if (!repository.existsById(id)) throw new WorkersDtoNotFound();
        repository.deleteById(id);
        return new ApiResponse();
    }

    public ApiResponse changePassword(WorkersChangePassword changePassword) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!encoder.matches(changePassword.getOldPassword(), workers.getPassword()))
            throw new WorkersDtoNotFound();
        workers.setPassword(encoder.encode(changePassword.getNewPassword()));
        repository.save(workers);
        return new ApiResponse();
    }

    public ApiResponse blockOrUnblock(Integer id, boolean enabled) {
        Workers workers = repository.findByIdAndEnabledIsTrue(id).orElseThrow(WorkersDtoNotFound::new);
        workers.setEnabled(enabled);
        workersRepository.save(workers);
        return new ApiResponse();
    }
}
