package uz.softex.securitystore.workers.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.dto.WorkersChangePassword;
import uz.softex.securitystore.workers.service.WorkersService;
import uz.softex.securitystore.workers.dto.WorkersUpdateDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/workers")
@SecurityRequirement(name = "javainuseapi")
public class WorkersController {
    private final
    WorkersService service;

    public WorkersController(WorkersService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_WORKERS')")
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_WORKERS')")
    @GetMapping("/{id}")
    public HttpEntity<?> all(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_WORKER')")
    @PatchMapping("/update")
    public HttpEntity<?> update(@Valid @RequestBody WorkersUpdateDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_WORKER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize(value = "hasAuthority('CHANGE_MY_PASSWORD')")
    @PostMapping("/changeMyPassword")
    public HttpEntity<?> change(@RequestBody WorkersChangePassword changePassword) {
        return ResponseEntity.ok(service.changePassword(changePassword));
    }
    @PreAuthorize(value = "hasAuthority('BLOCK_OR_UNBLOCK')")
    @GetMapping("/{enabled}/{id}")
    public HttpEntity<?> enabled(@PathVariable Integer id, @PathVariable boolean enabled) {
        return ResponseEntity.ok(service.blockOrUnblock(id, enabled));
    }
}
