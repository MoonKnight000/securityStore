package uz.softex.securitystore.position.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.position.repository.PositionRepository;
import uz.softex.securitystore.position.dto.PositionDto;
import uz.softex.securitystore.position.entity.Position;
import uz.softex.securitystore.position.exceptions.PositionNotFound;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.workers.service.AuthService;
import uz.softex.securitystore.workers.repository.WorkersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService {
    private final
    PositionRepository repository;
    private final
    StoreRepository storeRepository;
    private final
    WorkersRepository workersRepository;
    private final
    AuthService authService;

    public PositionService(PositionRepository repository, StoreRepository storeRepository, WorkersRepository workersRepository, AuthService authService) {
        this.repository = repository;
        this.storeRepository = storeRepository;
        this.workersRepository = workersRepository;
        this.authService = authService;
    }

    public ApiResponse add(PositionDto dto) {
        repository.save(new Position(dto));
        return new ApiResponse();
    }

    @Transactional
    public ApiResponse delete(Integer name) {
        if (!repository.existsById(name)) throw new PositionNotFound();
        workersRepository.deleteByPositionId(name);
        repository.deleteById(name);
        return new ApiResponse();
    }

    public ApiResponse update(PositionDto positionDto) {
        Position position = repository.findById(positionDto.getId()).orElseThrow(PositionNotFound::new);
        position.claim(positionDto);
        repository.save(position);
        return new ApiResponse();
    }

    public ApiResponseGeneric all() {
        List<Position> all = repository.findAll();
        List<PositionDto> positionDtoList = new ArrayList<>();
        all.forEach(i -> positionDtoList.add(new PositionDto(i)));
        return new ApiResponseGeneric<>();
    }

    public ApiResponseGeneric getOne(Integer id) {
        return new ApiResponseGeneric(repository.findById(id).orElseThrow(PositionNotFound::new));
    }

    public ApiResponseGeneric getMyPosition() {
        return new ApiResponseGeneric<>(authService.getCurrentWorker().getPosition());
    }
}
