package com.countries.capital.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonPathReaderUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonPathReaderUtil.class);

    public String extractValueWithJsonPath(Object jsonResponse, String jsonPath) {
        String jsonValue = "";
        try {
            JSONObject jsonObject = (JSONObject) jsonResponse;
            if (null != jsonObject) {
                jsonValue = (String) jsonObject.get(jsonPath);
                logger.info(jsonPath + " -> " + jsonValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonValue;
    }
}
