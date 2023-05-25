package uz.softex.securitystore.outputs.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.outputs.dto.OutputsDto;
import uz.softex.securitystore.outputs.service.OutputsService;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;

import javax.validation.Valid;

@RestController
@RequestMapping("/outputs")
@SecurityRequirement(name = "javainuseapi")
public class OutputsController {
    private final
    OutputsService service;

    public OutputsController(OutputsService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('ADD_OUTPUTS')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody OutputsDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_OUTPUTS')")
    @PatchMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody OutputsDto outputsDto) {
        return ResponseEntity.ok(service.update(outputsDto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_OUTPUTS')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_OUTPUTS')")
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }
}
