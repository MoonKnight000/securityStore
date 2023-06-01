package uz.softex.securitystore.address.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.softex.securitystore.address.entity.Address;
import uz.softex.securitystore.address.dto.AddressDto;
import uz.softex.securitystore.address.exception.AddressNotFound;
import uz.softex.securitystore.address.repository.AddressRepository;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.workers.dto.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AddressService {
    private final
    RestTemplate restTemplate;
    private final
    AddressRepository repository;

    public AddressService(AddressRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
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
        ApiResponseGeneric<AddressDto> addressDtoApiResponseGeneric = new ApiResponseGeneric<>(new AddressDto(repository.findById(id).orElseThrow(AddressNotFound::new)));
        HttpEntity<Login> login  = new HttpEntity<>(new Login("devshaha","12345678"));
        ApiResponse login1 = restTemplate.postForObject("http://localhost:8080/auth/login", login, ApiResponse.class);
        System.out.println(login1);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+login1.getMessage());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ApiResponseGeneric> entity = new HttpEntity<>(addressDtoApiResponseGeneric, headers);
        return restTemplate.exchange("http://localhost:8080/address",
                HttpMethod.GET,
                entity,
                ApiResponseGeneric.class).getBody();
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
