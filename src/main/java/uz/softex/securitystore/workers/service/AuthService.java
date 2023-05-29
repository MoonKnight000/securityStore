package uz.softex.securitystore.workers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.softex.securitystore.position.exceptions.PositionNotFound;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.position.entity.Position;
import uz.softex.securitystore.position.repository.PositionRepository;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.store.exceptions.StoreNotFound;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.dto.WorkersDto;
import uz.softex.securitystore.workers.repository.WorkersRepository;

@Service
public class AuthService {
    private final
    WorkersRepository workersRepository;
    private final
    PositionRepository positionRepository;
    private final
    StoreRepository storeRepository;
    private final
    PasswordEncoder encoder;

    public AuthService(WorkersRepository workersRepository, PositionRepository positionRepository, StoreRepository storeRepository, PasswordEncoder encoder) {
        this.workersRepository = workersRepository;
        this.positionRepository = positionRepository;
        this.storeRepository = storeRepository;
        this.encoder = encoder;
    }

    public ApiResponse register(WorkersDto workersDto) {

        Store store = storeRepository.findById(workersDto.getStore()).orElseThrow(StoreNotFound::new);
        Position position = positionRepository.findById(workersDto.getPosition()).orElseThrow(PositionNotFound::new);
        Workers workers = new Workers(workersDto, store, position);
        workers.setPassword(encoder.encode(workers.getPassword()));
        workersRepository.save(workers);
        return new ApiResponse();
    }

    public Workers getCurrentWorker() {
        return (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
