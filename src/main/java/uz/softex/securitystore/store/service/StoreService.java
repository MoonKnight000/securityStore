package uz.softex.securitystore.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.softex.securitystore.address.entity.Address;
import uz.softex.securitystore.address.repository.AddressRepository;
import uz.softex.securitystore.address.exception.AddressNotFound;
import uz.softex.securitystore.inputs.repository.InputsRepository;
import uz.softex.securitystore.outputs.repository.OutputsRepository;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.product.repository.ProductRepository;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.store.dto.StoreDto;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.store.exceptions.StoreNotFound;
import uz.softex.securitystore.workers.service.AuthService;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.repository.WorkersRepository;

import java.util.List;

@Service
public class StoreService {

   private final
    StoreRepository repository;
   private final
    AddressRepository addressRepository;
   private final
    InputsRepository inputsRepository;
   private final
    OutputsRepository outputsRepository;
   private final
    WorkersRepository workersRepository;
   private final
    ProductRepository productRepository;
   private final
    AuthService authService;

    public StoreService(StoreRepository repository, AddressRepository addressRepository, InputsRepository inputsRepository, OutputsRepository outputsRepository, WorkersRepository workersRepository, ProductRepository productRepository, AuthService authService) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.inputsRepository = inputsRepository;
        this.outputsRepository = outputsRepository;
        this.workersRepository = workersRepository;
        this.productRepository = productRepository;
        this.authService = authService;
    }

    public ApiResponseGeneric<StoreDto> getOne(Integer id) {
        Store store = repository.findById(id).orElseThrow(StoreNotFound::new);
        return new ApiResponseGeneric<>(new StoreDto(store));
    }

    public ApiResponseGeneric<StoreDto> getMyStore() {
        return new ApiResponseGeneric<>( new StoreDto(authService.getCurrentWorker().getStore()));
    }

    public ApiResponse add(StoreDto dto) {
        repository.save(new Store(dto,addressRepository.findById(dto.getAddress()).orElseThrow(AddressNotFound::new)));
        return new ApiResponse();
    }

    public ApiResponse update(Integer id, StoreDto dto) {
        Address address = addressRepository.findById(dto.getAddress()).orElseThrow(AddressNotFound::new);
        Store store = repository.findById(id).orElseThrow(StoreNotFound::new);
        store.setBalance(dto.getBalance());
        store = new Store(dto,address);
        repository.save(store);
        return new ApiResponse();
    }

    @Transactional
    public ApiResponse delete(Integer id) {
        if (!repository.existsById(id)) throw  new RuntimeException();
        List<Workers> byStoreId = workersRepository.findByStoreIdAndEnabledIsTrue(id);
        byStoreId.forEach(i -> {
            i.setStore(null);
            workersRepository.save(i);
        });
        inputsRepository.deleteByStoreId(id);
        outputsRepository.deleteByStoreId(id);
        productRepository.deleteByStoreId(id);
        repository.deleteById(id);
        return new ApiResponse();
    }
}
