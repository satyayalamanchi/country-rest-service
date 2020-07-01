package com.countries.capital;

import com.countries.capital.service.CountriesCapitalService;
import com.countries.capital.utils.RestCountriesServiceProps;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CountryServiceCapitalRetrieverTest {

    Logger logger = LoggerFactory.getLogger(CountryServiceCapitalRetrieverTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestCountriesServiceProps restCountriesServiceProps;

    @Autowired
    CountriesCapitalService countriesCapitalService;

    @Test
    public void getResponseWithCountryName_RetrieveCapitalCity_Test() throws Exception {
        String name = System.getProperty("name");
        if (StringUtils.isNotBlank(name)){
            countriesCapitalService.callServiceWithCountryName(name);
        }
        logger.info(new Throwable().getStackTrace()[0].getMethodName() + " -> Executed");
        logger.info("**************************************************************************");
    }

    @Test
    public void getResponseWithCountryCode_RetrieveCapitalCity_Test() throws Exception {
        String codes = System.getProperty("codes");
        if (StringUtils.isNotBlank(codes)){
            countriesCapitalService.callServiceWithCountryCodes(codes);
        }
        logger.info(new Throwable().getStackTrace()[0].getMethodName() + " -> Executed");
        logger.info("**************************************************************************");
    }
}
