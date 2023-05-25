package uz.softex.securitystore.store.contoller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.store.dto.StoreDto;
import uz.softex.securitystore.store.service.StoreService;

import javax.validation.Valid;

@RestController
@RequestMapping("/store")
@SecurityRequirement(name = "javainuseapi")
public class StoreController {
    private final
    StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('VIEW_STORE')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_STORE')")
    @GetMapping("/getMyStore")
    public HttpEntity<?> getOne() {
        return ResponseEntity.ok(service.getMyStore());
    }

    @PreAuthorize(value = "hasAuthority('ADD_STORE')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(service.add(storeDto));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_STORE')")
    @PatchMapping("/{id}")
    public HttpEntity<?> update(@PathVariable Integer id, @Valid @RequestBody StoreDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_STORE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
