package uz.softex.securitystore.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.product.dto.*;
import uz.softex.securitystore.product.entity.*;
import uz.softex.securitystore.product.exception.ProductNotFoundException;
import uz.softex.securitystore.product.repository.ClothRepository;
import uz.softex.securitystore.product.repository.DrinksRepository;
import uz.softex.securitystore.product.repository.ProductRepository;
import uz.softex.securitystore.workers.service.AuthService;
import uz.softex.securitystore.workers.entity.Workers;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final
    ProductRepository repository;
    private final
    ClothRepository clothRepository;
    private final
    DrinksRepository drinksRepository;
    private final AuthService authService;

    public ProductService(ClothRepository clothRepository, DrinksRepository drinksRepository, AuthService authService, ProductRepository repository) {
        this.clothRepository = clothRepository;
        this.drinksRepository = drinksRepository;
        this.authService = authService;
        this.repository = repository;
    }

    public ApiResponseGeneric getOne(Integer id) {
        return new ApiResponseGeneric<>(new ProductDto(repository.findByStore_IdAndIdAndDeletedIsFalse(
                        authService.getCurrentWorker().getStore().getId(), id)
                .orElseThrow(ProductNotFoundException::new)));
    }

    public ApiResponseGeneric getMyStoreProducts() {
        List<Products> allByStoreId = repository.findAllByStoreIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId());
        List<ProductDto> productDtoList = new ArrayList<>();
        allByStoreId.forEach(i -> {
            productDtoList.add(new ProductDto(i));
        });
        return new ApiResponseGeneric<>(productDtoList);
    }

    public ApiResponse add(@Valid ProductDto dto) {
        repository.save(new Products(dto, authService.getCurrentWorker().getStore()));
        return new ApiResponse();
    }

    public ApiResponse update(@Valid ProductDto dto) {
        Products products = repository.findByStore_IdAndIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId(), dto.getId())
                .orElseThrow(ProductNotFoundException::new);
        products = new Products(dto, products.getStore());
        repository.save(products);
        return new ApiResponse();
    }

    public ApiResponse delete(Integer id) {
        Workers principal = authService.getCurrentWorker();
        Products products = repository.findAllByStoreIdAndIdAndDeletedIsFalse(principal.getStore().getId(), id)
                .orElseThrow(ProductNotFoundException::new);
        products.setDeletedAt(LocalDateTime.now());
        products.setDeletedBy(principal);
        products.setDeleted(true);
        repository.save(products);
        return new ApiResponse();
    }


    public ApiResponseGeneric filterMap(Integer page, Integer size, Map<String, String> orderBy, ProductFilter filter) {
        Pageable pageable = PageRequest.of(page, size);
        if (orderBy != null) {
            List<String> row = List.of("name", "price", "count");
            for (String s : row) {
                if (orderBy.containsKey(s)) {
                    String a = orderBy.get(s);
                    if (a.equals("desc")) {
                        pageable = PageRequest.of(page, size, pageable.getSort().and(Sort.by(s).descending()));
                    } else pageable = PageRequest.of(page, size, pageable.getSort().and(Sort.by(s)));
                }
            }
        }

        System.out.println(pageable.getSort());
        Page<Products> search = repository.findByNameContainsAndCountBetweenAndPriceBetweenAndStoreIdAndDeletedIsFalse(filter.getName(), filter.getMinCount(), filter.getMaxCount(), filter.getMinPrice(), filter.getMaxPrice(), authService.getCurrentWorker().getStore().getId(), pageable);
        if (page > search.getTotalPages()) {
            pageable = setPage(search, pageable);
            search = repository.findByNameContainsAndCountBetweenAndPriceBetweenAndStoreIdAndDeletedIsFalse(filter.getName(), filter.getMinCount(), filter.getMaxCount(), filter.getMinPrice(), filter.getMaxPrice(), authService.getCurrentWorker().getStore().getId(), pageable);
        }
        List<Products> all = search.getContent();
        List<ProductDto> dtos = new ArrayList<>();
        all.forEach(i ->
                dtos.add(new ProductDto(i))
        );
        return new ApiResponseGeneric<>(new PageDto<>(pageable.getPageSize(), pageable.getPageNumber(), dtos));
    }


    public ApiResponse addTogether(List<@Valid ProductDto> productDtoList) {
        List<Products> productsList = new ArrayList<>();
        productDtoList.forEach(i -> {
            productsList.add(new Products(i, authService.getCurrentWorker().getStore()));
        });
        repository.saveAll(productsList);
        return new ApiResponse();
    }

    // CLOTHES

    public ApiResponse add(@Valid ClothDto dto) {
        clothRepository.save(new Cloth(dto, authService.getCurrentWorker().getStore()));
        return new ApiResponse();
    }

    public ApiResponseGeneric getAllClothes(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Cloth> all = clothRepository.findAllByStoreIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId(), pageable);
        if (page > all.getTotalPages()) {
            pageable = setPage(all, pageable);
            all = clothRepository.findAllByStoreIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId(), pageable);
        }
        List<Cloth> byStoreId = all.getContent();

        List<ClothDto> dtos = new ArrayList<>();
        byStoreId.forEach(i -> dtos.add(new ClothDto(i)));
        return new ApiResponseGeneric<>( new PageDto(size, pageable.getPageNumber(), dtos));
    }

    public ApiResponse updateCloth(@Valid ClothDto dto) {
        try {
            Cloth cloth = clothRepository.findByStore_IdAndIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId(), dto.getId())
                    .orElseThrow(ProductNotFoundException::new);
            cloth = new Cloth(dto, cloth.getStore());
            clothRepository.save(cloth);
            return new ApiResponse(" yangilandi", true);
        } catch (Exception e) {
            return new ApiResponse("Bu kiyim mavjud ", false);
        }
    }

    public ApiResponseGeneric filterClothes(@Valid ClothFilter filter, Integer page, Integer size) {

        List<ClothSize> clothSizes = filter.getClothSize();

        List<TypeCloth> typeCloths = filter.getTypeCloth();

        List<Color> colors = filter.getColor();

        List<String> clothSizesString = new ArrayList<>();

        clothSizes.forEach(i -> clothSizesString.add(String.valueOf(i)));
        List<String> typeClothsString = new ArrayList<>();
        typeCloths.forEach(i -> typeClothsString.add(String.valueOf(i)));

        List<String> colorsString = new ArrayList<>();
        colors.forEach(i -> colorsString.add(String.valueOf(i)));

        Pageable pageable = PageRequest.of(page, size);
        Page<Cloth> all = clothRepository
                .findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndColorContainsAndClothSizeAndTypeClothAndDeletedIsFalse(
                        filter.getName(), filter.getMinCount(), filter.getMaxCount(), authService.getCurrentWorker().getStore().getId(), filter.getMinPrice(),
                        filter.getMaxPrice(), colorsString, clothSizesString, typeClothsString
                        , pageable);
        if (page > all.getTotalPages()) {
            pageable = setPage(all, pageable);
            all = clothRepository
                    .findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndColorContainsAndClothSizeAndTypeClothAndDeletedIsFalse(
                            filter.getName(), filter.getMinCount(), filter.getMaxCount(), authService.getCurrentWorker().getStore().getId(), filter.getMinPrice(),
                            filter.getMaxPrice(), colorsString, clothSizesString, typeClothsString
                            , pageable);
        }

        List<Cloth> result = all.getContent();
        System.out.println(result);
        List<ClothDto> dtoList = new ArrayList<>();
        result.forEach(i -> dtoList.add(new ClothDto(i)));
        return new ApiResponseGeneric<>(new PageDto<>(size, pageable.getPageNumber(), dtoList));

    }

    public ApiResponse addTogetherCloth(List<@Valid ClothDto> productDtoList) {
        List<Cloth> productsList = new ArrayList<>();
        productDtoList.forEach(i -> {
                    productsList.add(new Cloth(i, authService.getCurrentWorker().getStore()));
                }
        );
        clothRepository.saveAll(productsList);
        return new ApiResponse();
    }

    // DRINKS

    public ApiResponse add(@Valid DrinksDto dto) {
        drinksRepository.save(new Drinks(dto, authService.getCurrentWorker().getStore()));
        return new ApiResponse("saqlandi", true);
    }

    public ApiResponseGeneric getAllDrinks(Integer page, Integer size) {

        Workers workers = authService.getCurrentWorker();

        Pageable pageable = PageRequest.of(page, size);
        Page<Drinks> all = drinksRepository.findByStore_IdAndDeletedIsFalse(workers.getStore().getId(), pageable);

        if (page > all.getTotalPages()) {
            pageable = setPage(all, pageable);
            all = drinksRepository.findByStore_IdAndDeletedIsFalse(workers.getStore().getId(), pageable);
        }

        List<Drinks> byStoreId = all.getContent();
        List<DrinksDto> dtoList = new ArrayList<>();
        byStoreId.forEach(i -> dtoList.add(new DrinksDto(i)));
        return new ApiResponseGeneric<>(" olindi ", true, new PageDto<>(size, pageable.getPageNumber(), dtoList));
    }

    public ApiResponse updateDrinks(@Valid DrinksDto dto) {
        Drinks drinks = drinksRepository.findByStore_IdAndIdAndDeletedIsFalse(authService.getCurrentWorker().getStore().getId(), dto.getId()).orElseThrow(ProductNotFoundException::new);
        drinks = new Drinks(dto, drinks.getStore());
        drinksRepository.save(drinks);
        return new ApiResponse();
    }

    public ApiResponseGeneric DrinksFilter(@Valid DrinksFilter filter, Integer page, Integer size) {

        List<DrinksType> type = filter.getType();
        List<String> typeString = new ArrayList<>();
        type.forEach(i -> typeString.add(String.valueOf(i)));

        Pageable pageable = PageRequest.of(page, size);
        Page<Drinks> all = drinksRepository.findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndBrandContainingAndTypeAndDeletedIsFalse
                (filter.getName(), filter.getMinCount(), filter.getMaxCount(), authService.getCurrentWorker().getStore().getId(),
                        filter.getMinPrice(), filter.getMaxPrice(), filter.getBrand(), typeString, pageable);
        if (page > all.getTotalPages()) {
            pageable = setPage(all, pageable);
            all = drinksRepository.findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndBrandContainingAndTypeAndDeletedIsFalse
                    (filter.getName(), filter.getMinCount(), filter.getMaxCount(), authService.getCurrentWorker().getStore().getId(),
                            filter.getMinPrice(), filter.getMaxPrice(), filter.getBrand(), typeString, pageable);
        }
        List<Drinks> result = all.getContent();
        if (result.isEmpty()) throw new ProductNotFoundException();
        List<DrinksDto> dtoList = new ArrayList<>();
        result.forEach(i -> dtoList.add(new DrinksDto(i)));
        return new ApiResponseGeneric(new PageDto<>(size, pageable.getPageNumber(), dtoList));
    }

    public ApiResponse addTogetherDrinks(List<@Valid DrinksDto> productDtoList) {
        List<Drinks> productsList = new ArrayList<>();
        productDtoList.forEach(i ->productsList.add(new Drinks(i, authService.getCurrentWorker().getStore())));
        drinksRepository.saveAll(productsList);
        return new ApiResponse();
    }

    public Pageable setPage(Page page, Pageable pageable) {
        Integer pageNumber = page.getTotalPages() - 1;
        pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        return pageable;
    }

}