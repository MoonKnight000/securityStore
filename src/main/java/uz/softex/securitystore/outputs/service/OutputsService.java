package uz.softex.securitystore.outputs.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.softex.securitystore.inputs.entity.PaymentType;
import uz.softex.securitystore.inputs.exceptions.PaymentTypeNotFound;
import uz.softex.securitystore.inputs.repository.PaymentTypeRepository;
import uz.softex.securitystore.outputs.repository.OutputsRepository;
import uz.softex.securitystore.outputs.dto.OutputsDto;
import uz.softex.securitystore.outputs.entity.Outputs;
import uz.softex.securitystore.outputs.exeptions.OutputsNotFound;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.product.exception.ProductNotFoundException;
import uz.softex.securitystore.product.repository.ProductRepository;
import uz.softex.securitystore.product.entity.Products;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.workers.entity.Workers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutputsService {
   private   final
    OutputsRepository repository;
   private final
    StoreRepository storeRepository;
   private final
    ProductRepository productRepository;
   private final
    PaymentTypeRepository paymentTypeRepository;

    public OutputsService(OutputsRepository repository, StoreRepository storeRepository, ProductRepository productRepository, PaymentTypeRepository paymentTypeRepository) {
        this.repository = repository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public ApiResponseGeneric all() {
        List<Outputs> all = repository.findAll();
        List<OutputsDto> dtos = new ArrayList<>();
        all.forEach(i -> {
            dtos.add(new OutputsDto(i));
        });
        return new ApiResponseGeneric<>(dtos);
    }

    @Transactional
    public ApiResponse add(OutputsDto dto) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Store store = workers.getStore();

        Products products = productRepository.findByStore_IdAndIdAndDeletedIsFalse(store.getId(), dto.getProducts()).orElseThrow(ProductNotFoundException::new);
        PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentType()).orElseThrow(PaymentTypeNotFound::new);

        Outputs outputs = new Outputs(dto, products, paymentType, store);

        store.setBalance(store.getBalance() - outputs.getAmount());
        storeRepository.save(store);

        products.setCount(products.getCount() - outputs.getCount());
        productRepository.save(products);

        repository.save(outputs);

        return new ApiResponse();
    }

    @Transactional
    public ApiResponse update(OutputsDto dto) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = workers.getStore();

        Outputs outputs = repository.findByStore_IdAndId(store.getId(), dto.getId()).orElseThrow(OutputsNotFound::new);

        Products products = outputs.getProducts();
        products.setCount(products.getCount() + outputs.getCount());
        productRepository.save(products);
        store.setBalance(store.getBalance() + outputs.getAmount());

        Products newProducts = productRepository.findByStore_IdAndIdAndDeletedIsFalse(store.getId(), dto.getProducts()).orElseThrow(ProductNotFoundException::new);
        PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentType()).orElseThrow(PaymentTypeNotFound::new);

        outputs = new Outputs(dto, newProducts, paymentType, store);
        newProducts.setCount(newProducts.getCount()-outputs.getCount());
        store.setBalance(store.getBalance() - outputs.getAmount());
        storeRepository.save(store);
        repository.save(outputs);
        productRepository.save(newProducts);
        return new ApiResponse();
    }

    public ApiResponse delete(Integer id) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Outputs> outputsOptional = repository.findByStore_IdAndId(workers.getStore().getId(), id);
        if (outputsOptional.isEmpty()) return new ApiResponse(" bunday chiqimmmavjud emas", false);
        Outputs outputs = outputsOptional.get();
        Products products = outputs.getProducts();
        products.setCount(products.getCount() - outputs.getCount());
        productRepository.save(products);
        Store store = workers.getStore();

        store.setBalance(store.getBalance() + outputs.getCount() * products.getPrice());
        storeRepository.save(store);

        repository.deleteById(id);
        return new ApiResponse("o`chirildi", true);
    }
}
