package uz.softex.securitystore.currency;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@SecurityRequirement(name = "javainuseapi")
public class CurrencyController {
    private final
    CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }
@PreAuthorize(value = "hasAuthority('ADD_INPUTS')")
    @GetMapping
    public HttpEntity all() {
        return ResponseEntity.ok(service.getAll());
    }
}
