package com.countries.capital.service;

import com.countries.capital.utils.RestCountriesServiceProps;
import com.countries.capital.validator.CountryCapitalServiceValidator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Data
@Service
public class CountriesCapitalService
{
    Logger logger = LoggerFactory.getLogger(CountriesCapitalService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestCountriesServiceProps restCountriesServiceProps;

    @Autowired
    CountryCapitalServiceValidator countryCapitalServiceValidator;

    public void callServiceWithCountryName(String name) throws JSONException {
        callRestCountryService(restCountriesServiceProps.getNameUri()+name);
    }

    public void callServiceWithCountryCodes(String codes) throws JSONException {
        callRestCountryService(restCountriesServiceProps.getRestCountriesUriForCodes(codes));
    }

    private void callRestCountryService(String restCountriesUri) throws JSONException {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(restCountriesUri, String.class);
            assertNotNull(responseEntity);
            if (null != responseEntity) {
                logger.info("Status Code -> " + responseEntity.getStatusCode());
                logger.info("Response Body -> " + responseEntity.getBody());
                countryCapitalServiceValidator.validateResponseOfCountryService(responseEntity);
            }
        } catch(HttpClientErrorException e){
            logger.info("Status Code -> " + e.getStatusCode());
            countryCapitalServiceValidator.validateBadRequestFromCountryService(e);
        } catch (HttpServerErrorException e) {
            logger.info("Status Code -> " + e.getStatusCode());
        }
    }
}
