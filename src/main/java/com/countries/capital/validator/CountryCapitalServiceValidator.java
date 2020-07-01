package com.countries.capital.validator;

import com.countries.capital.utils.JsonPathReaderUtil;
import com.countries.capital.utils.RestCountriesServiceProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONTokener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@Component
public class CountryCapitalServiceValidator {
    private static Logger logger = LoggerFactory.getLogger(CountryCapitalServiceValidator.class);

    @Autowired
    JsonPathReaderUtil jsonPathReaderUtil;

    @Autowired
    RestCountriesServiceProps restCountriesServiceProps;

    public void validateResponseOfCountryService(ResponseEntity<String> responseEntity) throws JSONException {
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        String responseEntityBody = responseEntity.getBody();
        assertNotEquals(responseEntityBody, "");

        Object object = new JSONTokener(responseEntityBody).nextValue();
        if(object instanceof JSONArray) {
            JSONArray jsonArray = new JSONArray(responseEntityBody);
            for(int i = 0; i < jsonArray.length() ; i++) {
                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.get(i)));
                assertNotEquals(jsonPathReaderUtil.extractValueWithJsonPath(jsonObject, restCountriesServiceProps.getCapitalJsonPath()), "");
            }
        } else if(object instanceof JSONObject) {
            JSONObject jsonObject = new JSONObject(responseEntityBody);
            assertNotEquals(jsonPathReaderUtil.extractValueWithJsonPath(jsonObject, restCountriesServiceProps.getCapitalJsonPath()), "");
        }

    }

    public void validateBadRequestFromCountryService(HttpClientErrorException e) {
        assertEquals(e.getRawStatusCode(), 404);
    }
}
