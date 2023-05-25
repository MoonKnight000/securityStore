package uz.softex.securitystore.position.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.position.dto.PositionDto;
import uz.softex.securitystore.position.service.PositionService;


@RestController
@RequestMapping("/position")
@SecurityRequirement(name = "javainuseapi")
public class PositionController {
    private final
    PositionService service;

    public PositionController(PositionService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('ADD_POSITION')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody PositionDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_POSITION')")
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @PreAuthorize(value = "hasAuthority('VIEW_POSITION')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_POSITION')")
    @GetMapping("/getMyPosition")
    public HttpEntity<?> GetMyPosition() {
        return ResponseEntity.ok(service.getMyPosition());
    }

    @PreAuthorize(value = "hasAuthority('EDIT_POSITION')")
    @PatchMapping("/getOne")
    public HttpEntity<?> update(@RequestBody PositionDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_POSITION')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
