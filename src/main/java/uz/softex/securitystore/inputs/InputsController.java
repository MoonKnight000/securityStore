package uz.softex.securitystore.inputs;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.inputs.dto.InputsDto;
import uz.softex.securitystore.inputs.dto.StatisticsType;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@RequestMapping("/saleProducts")
@SecurityRequirement(name = "javainuseapi")

public class InputsController {
    private  final
    InputsService service;

    public InputsController(InputsService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('ADD_INPUTS')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody InputsDto inputsDto) {
        return ResponseEntity.ok(service.add(inputsDto));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('ADD_INPUTS')")
    @PatchMapping("/{id}")
    public HttpEntity<?> update(@Valid @RequestBody InputsDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_INPUTS')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @PostMapping("/statistics")
    public HttpEntity<?> statistics(@RequestParam StatisticsType statisticsType, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.statistics(statisticsType, size, page));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @PostMapping("/saleBetweenPrice")
    public HttpEntity<?> saledBetweenPrice(@RequestParam(defaultValue = "2023-05-15 00:00:25.640897900") String start, @RequestParam(defaultValue = "2023-05-22 00:00:25.640897900") String finish, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.saledAllPrice(page, size, Timestamp.valueOf(start), Timestamp.valueOf(finish)));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @PostMapping("/saleBetweenNumber")
    public HttpEntity<?> saledBetweenNumber(@RequestParam(defaultValue = "2023-05-15 00:00:25.640897900") String start, @RequestParam(defaultValue = "2023-05-22 00:00:25.640897900") String finish, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.saledAllCount(page, size, Timestamp.valueOf(start), Timestamp.valueOf(finish)));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @PostMapping("/saleTop")
    public HttpEntity<?> saleTop(@RequestParam(defaultValue = "10") Integer topNumber) {
        return ResponseEntity.ok(service.saleTop(topNumber));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_INPUTS')")
    @GetMapping("/getFile")
    public HttpEntity<?> saleTop(HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(service.uploadExcel(response));
    }
}
