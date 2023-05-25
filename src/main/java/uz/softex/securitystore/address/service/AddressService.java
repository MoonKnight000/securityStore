package uz.softex.securitystore.address.service;

import org.springframework.stereotype.Service;
import uz.softex.securitystore.address.entity.Address;
import uz.softex.securitystore.address.dto.AddressDto;
import uz.softex.securitystore.address.exception.AddressNotFound;
import uz.softex.securitystore.address.repository.AddressRepository;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final
    AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public ApiResponseGeneric all() {
        List<Address> all = repository.findAll();
        List<AddressDto> dtoList = new ArrayList<>();
        all.forEach(i ->
            dtoList.add(new AddressDto(i))
        );
        return new ApiResponseGeneric<>("olindi ", true, dtoList);
    }

    public ApiResponseGeneric getOne(Integer id) {
        return new ApiResponseGeneric<>(new ApiResponseGeneric<>(new AddressDto(repository.findById(id).orElseThrow(AddressNotFound::new))));
    }

    public ApiResponse add(AddressDto dto) {
        repository.save(new Address(dto));
        return new ApiResponse();
    }

    public ApiResponse update(AddressDto dto) {
        Address address = repository.findById(dto.getId()).orElseThrow(AddressNotFound::new);
        address.claim(dto);
        repository.save(address);
        return new ApiResponse();
    }

    public ApiResponse delete(Integer id) {
        if (!repository.existsById(id)) throw new AddressNotFound();
        repository.deleteById(id);
        return new ApiResponse();
    }
}
