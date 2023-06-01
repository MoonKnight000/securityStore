package uz.softex.securitystore.currency;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.softex.securitystore.currency.entity.Currency;
import uz.softex.securitystore.currency.repository.CurrencyRepository;

import java.util.ArrayList;

import java.util.List;

@Service
public class CurrencyService {

    final
    CurrencyRepository repository;
    final
    RestTemplate restTemplate;


    public CurrencyService(CurrencyRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Currency[] getAll() {
        List<Currency> currencies = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity<>(currencies, headers);
        return restTemplate.exchange("https://cbu.uz/uz/arkhiv-kursov-valyut/json/",
                HttpMethod.GET,
                entity,
                Currency[].class).getBody();
    }
}
