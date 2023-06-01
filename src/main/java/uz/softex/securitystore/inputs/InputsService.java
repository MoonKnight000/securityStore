package uz.softex.securitystore.inputs;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import uz.softex.securitystore.inputs.dto.*;
import uz.softex.securitystore.inputs.entity.Inputs;
import uz.softex.securitystore.inputs.exceptions.InputsNotFound;
import uz.softex.securitystore.inputs.exceptions.PaymentTypeNotFound;
import uz.softex.securitystore.inputs.repository.InputsRepository;
import uz.softex.securitystore.inputs.repository.PaymentTypeRepository;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.payload.ApiResponseGeneric;
import uz.softex.securitystore.product.exception.ProductNotFoundException;
import uz.softex.securitystore.product.repository.ProductRepository;
import uz.softex.securitystore.product.entity.Products;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;
import uz.softex.securitystore.saledProducts.dto.SaledProductsCountDto;
import uz.softex.securitystore.saledProducts.repository.SaledProductsCountRepository;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.store.repository.StoreRepository;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InputsService {
    private final
    InputsRepository repository;
    private final
    ProductRepository productRepository;
    private final
    PaymentTypeRepository paymentTypeRepository;
    private final
    StoreRepository storeRepository;
    private final
    SaledProductsCountRepository saledProductsCountRepository;
    private final
    AuthService authService;

    public InputsService(InputsRepository repository, ProductRepository productRepository, PaymentTypeRepository paymentTypeRepository, StoreRepository storeRepository, SaledProductsCountRepository saledProductsCountRepository, AuthService authService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.storeRepository = storeRepository;
        this.saledProductsCountRepository = saledProductsCountRepository;
        this.authService = authService;
    }

    public ApiResponseGeneric all() {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Inputs> byStoreId = repository.findByStore_Id(workers.getStore().getId());
        List<InputsDto> inputsDtos = new ArrayList<>();
        byStoreId.forEach(i -> {
            System.out.println(i);
            if (i != null) {
                InputsDto inputsDto = new InputsDto(i);
                inputsDtos.add(inputsDto);
            }
        });
        return new ApiResponseGeneric<>(inputsDtos);
    }

    public ApiResponseGeneric getOne(Integer id) {

        Inputs inputs = repository.findByIdAndStoreId(id, authService.getCurrentWorker().getStore().getId()).orElseThrow(InputsNotFound::new);
        InputsDto inputsDto = new InputsDto(inputs);
        return new ApiResponseGeneric(inputsDto);
    }

    @Transactional
    public ApiResponse add(InputsDto dto) {

        Store store = authService.getCurrentWorker().getStore();

        List<Products> productsList = new ArrayList<>();
        List<SaledProductsCount> saledProductsCountsList = new ArrayList<>();
        List<SaledProductsCountDto> countDtos = dto.getSalesProducts();

        Double amount = 0.0;

        for (SaledProductsCountDto countDto : countDtos) {
            Products products = productRepository.findByStore_IdAndIdAndDeletedIsFalse(store.getId(), countDto.getProducts()).orElseThrow(ProductNotFoundException::new);

            if (products.getCount() - countDto.getCount() < 0) throw new ProductNotFoundException();
        }

        for (SaledProductsCountDto countDto : countDtos) {
            Products products = productRepository.findByStore_IdAndIdAndDeletedIsFalse(store.getId(), countDto.getProducts()).orElseThrow(ProductNotFoundException::new);
            products.setCount(products.getCount() - countDto.getCount());
            saledProductsCountsList.add(new SaledProductsCount(countDto, products));
            productsList.add(products);
            amount += products.getPrice() * countDto.getCount();
        }

        productRepository.saveAll(productsList);
//        saledProductsCountRepository.saveAll(saledProductsCountsList);

        repository.save(new Inputs(dto, paymentTypeRepository.findById(dto.getPaymentType()).orElseThrow(PaymentTypeNotFound::new), store, productsList, countDtos));

        store.setBalance(store.getBalance() + amount);
        storeRepository.save(store);
        return new ApiResponse();
    }

    @Transactional
    public ApiResponse update(InputsDto dto) {

        Store store = authService.getCurrentWorker().getStore();
        Inputs updated = repository.findByIdAndStoreId(dto.getId(), store.getId()).orElseThrow(InputsNotFound::new);


        List<Products> productsList = new ArrayList<>();
        List<SaledProductsCount> saledProductsCountsList = new ArrayList<>();
        List<SaledProductsCountDto> countDtos = dto.getSalesProducts();


        List<SaledProductsCount> saledProductsCounts = updated.getSaledProductsCounts();

        for (SaledProductsCount saledProductsCount : saledProductsCounts) {
            Products products = saledProductsCount.getProducts();
            products.setCount(products.getCount() + saledProductsCount.getCount());
            saledProductsCountRepository.deleteById(saledProductsCount.getId());
            productRepository.save(products);
        }

        for (SaledProductsCountDto countDto : countDtos) {

            Products products = productRepository.findByStore_IdAndIdAndDeletedIsFalse(store.getId(), countDto.getProducts()).orElseThrow(ProductNotFoundException::new);

            if (products.getCount() - countDto.getCount() < 0) return new ApiResponse(" miqdori yetmayapti", false);

            products.setCount(products.getCount() - countDto.getCount());

            saledProductsCountsList.add(new SaledProductsCount(countDto, products));
            productsList.add(products);

            dto.setAmount(products.getPrice() * countDto.getCount());
        }
        productRepository.saveAll(productsList);
//        saledProductsCountRepository.saveAll(saledProductsCountsList);


        store.setBalance(store.getBalance() - updated.getAmount() + dto.getAmount());
        updated = new Inputs(dto, paymentTypeRepository.findById(dto.getPaymentType()).orElseThrow(PaymentTypeNotFound::new), store, productsList, countDtos);

        repository.save(updated);

        storeRepository.save(store);
        return new ApiResponse();
    }

    @Transactional
    public ApiResponse delete(Integer id) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = workers.getStore();

        Inputs inputs = repository.findByIdAndStoreId(id, store.getId()).orElseThrow(InputsNotFound::new);
        List<SaledProductsCount> saledProductsCounts = inputs.getSaledProductsCounts();
        for (SaledProductsCount saledProductsCount : saledProductsCounts) {
            Products products = saledProductsCount.getProducts();
            products.setCount(products.getCount() + saledProductsCount.getCount());
            saledProductsCountRepository.deleteById(saledProductsCount.getId());
            productRepository.save(products);
        }
        store.setBalance(store.getBalance() - inputs.getAmount());
        storeRepository.save(store);
        repository.deleteById(id);
        return new ApiResponse("o`chirildi", true);
    }

    public ApiResponseGeneric statistics(StatisticsType statisticsType, Integer size, Integer page) {

        LocalDateTime now = LocalDateTime.now();
        if (statisticsType == StatisticsType.YEARLY) now = now.minusYears(1);
        else if (statisticsType == StatisticsType.MONTHLY) now = now.minusMonths(1);
        else if (statisticsType == StatisticsType.WEEKLY) now = now.minusDays(7);
        else if (statisticsType == StatisticsType.DAILY) now = now.minusDays(1);
        now = now.minusHours(now.getHour());
        now = now.minusMinutes(now.getMinute());

        return new ApiResponseGeneric<>(saledBetween(page, size, Timestamp.valueOf(now), Timestamp.valueOf(LocalDateTime.now())));
    }

    public List<StatisticsDto> saledBetween(Integer page, Integer size, Timestamp start, Timestamp finish) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<Inputs> result = repository.findByStore_IdAndCreatedTimeBetween(workers.getStore().getId(), start, finish, pageable);
        List<Inputs> content = result.getContent();

        Map<String, StatisticsDto> products = new HashMap<>();
        content.forEach(i -> {
            List<SaledProductsCount> saledProducts = i.getSaledProductsCounts();
            saledProducts.forEach(j -> {
                StatisticsDto dto = new StatisticsDto(j);

                if (products.containsKey(dto.getProductName()))
                    dto.add(products.get(dto.getProductName()));


                products.put(dto.getProductName(), dto);
            });
        });
        List<StatisticsDto> finishData = new ArrayList<>();
        for (String string : products.keySet()) finishData.add(products.get(string));

        return finishData;
    }

    public ApiResponseGeneric saledAllPrice(Integer page, Integer size, Timestamp start, Timestamp finish) {
        List<StatisticsDto> statisticsDtos = saledBetween(page, size, start, finish);
        List<StatisticsAllPrice> statisticsAllPrices = new ArrayList<>();
        statisticsDtos.forEach(i -> {
            StatisticsAllPrice allPrice = new StatisticsAllPrice();
            allPrice.setAmount(i.getAmount());
            allPrice.setName(i.getProductName());
            statisticsAllPrices.add(allPrice);
        });
        return new ApiResponseGeneric<>("olindi", true, statisticsAllPrices);
    }

    public ApiResponseGeneric saledAllCount(Integer page, Integer size, Timestamp start, Timestamp finish) {
        List<StatisticsDto> statisticsDtos = saledBetween(page, size, start, finish);
        List<StatisticsAllCount> statisticsAllCount = new ArrayList<>();
        statisticsDtos.forEach(i -> {
            StatisticsAllCount allPrice = new StatisticsAllCount();
            allPrice.setCount(i.getCount());
            allPrice.setProductName(i.getProductName());
            statisticsAllCount.add(allPrice);
        });
        return new ApiResponseGeneric<>("olindi", true, statisticsAllCount);
    }

    public ApiResponseGeneric saleTop(Integer topNumber) {
        Workers workers = (Workers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Inputs> byStoreId = repository.findByStoreId(workers.getStore().getId());

        Map<String, StatisticsDto> products = new HashMap<>();
        byStoreId.forEach(i -> {
            List<SaledProductsCount> saledProducts = i.getSaledProductsCounts();
            saledProducts.forEach(j -> {
                StatisticsDto dto = new StatisticsDto(j);

                if (products.containsKey(dto.getProductName()))
                    dto.add(products.get(dto.getProductName()));

                System.out.println(dto);
                products.put(dto.getProductName(), dto);
            });
        });
        List<StatisticsDto> finishData = new ArrayList<>();
        for (String string : products.keySet()) finishData.add(products.get(string));

        List<StatisticsDto> result = new ArrayList<>();
        for (int i = 0; i < topNumber; i++) {
            Integer max = 0;
            StatisticsDto maxDto = new StatisticsDto();
            for (StatisticsDto finishDatum : finishData) {
                if (finishDatum.getCount() > max) {
                    max = finishDatum.getCount();
                    maxDto = finishDatum;
                }
            }
            if (maxDto.getCount() != null) {
                result.add(maxDto);
                finishData.remove(maxDto);
            }
        }
        return new ApiResponseGeneric<>("olindi ", true, result);
    }

    public ApiResponseGeneric uploadExcel(HttpServletResponse response) throws Exception {
        Workers currentWorker = authService.getCurrentWorker();
        XSSFWorkbook workbook = new XSSFWorkbook();
        File file = new File("F:\\/excel.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        XSSFSheet sheet = workbook.createSheet("First");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("amount");
        row.createCell(2).setCellValue("paymentType");
        row.createCell(3).setCellValue("ProductName");
        row.createCell(4).setCellValue("Saled count");
        List<Inputs> byStoreId = repository.findByStore_Id(currentWorker.getStore().getId());
        Integer rowNumber = 1;
        for (int i = 0; i < byStoreId.size(); i++) {
            Inputs inputs = byStoreId.get(i);
            row = sheet.createRow(i + rowNumber);
            row.createCell(0).setCellValue(inputs.getId());
            row.createCell(1).setCellValue(inputs.getAmount());
            row.createCell(2).setCellValue(inputs.getPaymentType().getPaymentType());
            List<SaledProductsCount> saledProductsCounts = inputs.getSaledProductsCounts();
            for (int j = 0; j < saledProductsCounts.size(); j++) {
                SaledProductsCount saledProductsCount = saledProductsCounts.get(j);
                row.createCell(3).setCellValue(saledProductsCount.getProducts().getName());
                row.createCell(4).setCellValue(saledProductsCount.getCount());
                if (j + 1 < saledProductsCounts.size()) {
                    rowNumber++;
                    row = sheet.createRow(i + rowNumber);
                }
            }
            for (int j = 0; j < 5; j++) {
                sheet.autoSizeColumn(j);
            }
        }
        workbook.write(fileOutputStream);
        workbook.close();
        response.setHeader("Content-Disposition", "attachment; filename=\"excel.xlsx\"");
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponseGeneric<>();
    }
}
