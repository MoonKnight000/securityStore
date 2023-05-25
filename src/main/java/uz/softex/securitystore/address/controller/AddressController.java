package uz.softex.securitystore.address.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.address.dto.AddressDto;
import uz.softex.securitystore.address.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
@SecurityRequirement(name = "javainuseapi")
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('ADD_ADDRESS')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody AddressDto address) {
        return ResponseEntity.ok(service.add(address));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ADDRESS')")
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ADDRESS')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ADDRESS')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_ADDRESS')")
    @PatchMapping
    public HttpEntity<?> update(@Valid @RequestBody AddressDto address) {
        return ResponseEntity.ok(service.update(address));
    }
}
