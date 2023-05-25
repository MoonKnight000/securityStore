package uz.softex.securitystore.product.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.hibernate.hql.internal.ast.tree.ResolvableNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.product.dto.*;
import uz.softex.securitystore.product.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "javainuseapi")

public class ProductController {
    private final
    ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_PRODUCTS')")
    @GetMapping
    public HttpEntity all() {
        return ResponseEntity.ok(service.getMyStoreProducts());
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_PRODUCTS')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PatchMapping("/{id}")
    public HttpEntity<?> update(@Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize(value = "hasAuthority('FILTER_PRODUCTS')")
    @PostMapping("/ProductOrderBYAndFilter")
    public HttpEntity<?> filterMap(@RequestBody @Valid ProductFilter filter, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.filterMap(page, size, filter.getMap(), filter));
    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("addSeveral")
    public HttpEntity<?> addTogetherAll(@RequestBody List<@Valid ProductDto> dto) {
        return ResponseEntity.ok(service.addTogether(dto));
    }

    // CLOTHES

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/addCloth")
    public HttpEntity<?> addCloth(@Valid @RequestBody ClothDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_PRODUCTS')")
    @GetMapping("/getAllCloth")
    public HttpEntity<?> getAllClothes(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.getAllClothes(page, size));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PatchMapping("/updateCloth")
    public HttpEntity<?> updateCloth(@Valid @RequestBody ClothDto dto) {
        return ResponseEntity.ok(service.updateCloth(dto));
    }

    @PreAuthorize(value = "hasAuthority('FILTER_PRODUCTS')")
    @PostMapping("/filterColth")
    public HttpEntity<?> filterCloth(@RequestBody ClothFilter filter, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.filterClothes(filter, page, size));
    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/addSeveralCloths")
    public HttpEntity<?> addTogetherClothAll(@Valid @RequestBody List<ClothDto> dto) {
        return ResponseEntity.ok(service.addTogetherCloth(dto));
    }

    // DRINKS

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/addDrinks")
    public HttpEntity<?> addDrinks(@Valid @RequestBody DrinksDto drinksDto) {
        return ResponseEntity.ok(service.add(drinksDto));
    }

    @PreAuthorize(value = "hasAuthority('VIEW_MY_PRODUCTS')")
    @GetMapping("/getAllDrinks")
    public HttpEntity<?> getAllDrinks(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.getAllDrinks(page, size));
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PatchMapping("/updateDrinks")
    public HttpEntity<?> updateDrinks(@RequestBody DrinksDto drinksDto) {
        return ResponseEntity.ok(service.updateDrinks(drinksDto));
    }

    @PreAuthorize(value = "hasAuthority('FILTER_PRODUCTS')")
    @PostMapping("/DrinksFilter")
    public HttpEntity<?> filter(@RequestBody DrinksFilter filter, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(service.DrinksFilter(filter, page, size));
    }

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/addSeveralDrinks")
    public HttpEntity<?> addTogetherDrinks(@Valid @RequestBody List<DrinksDto> dto) {
        ApiResponse add = service.addTogetherDrinks(dto);
        return ResponseEntity.ok(service.addTogetherDrinks(dto));
    }
}