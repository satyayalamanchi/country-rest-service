package com.countries.capital.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class UserInputPromptUtil {

    private static Logger logger = LoggerFactory.getLogger(UserInputPromptUtil.class);

    final String REST_COUNTRIES_URL = "https://restcountries.eu/rest/v2";
    final String NAME_ENDPOINT = "/name/";
    final String CODE_ENDPOINT = "/alpha/";
    final String CODES_ENDPOINT = "/alpha?codes=";
    final String CAPITAL_PATH = "capital";

    static Scanner scanner = new Scanner(System.in);

    public void acceptUserInputAndAssertResponse() {
        boolean programExitInd = true;
        while (programExitInd) {
            logger.info("\nDo you want to Continue? Y/N");
            if(!scanner.hasNext()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String continueInd = scanner.next();
            if(continueInd.equalsIgnoreCase("n")) {
                logger.info("\nExiting the application");
                System.exit(0);
            } else if(continueInd.equalsIgnoreCase("y")) {
                logger.info("\nContinuing the application");
                logger.info("\nSelect any one from below options:\n"+
                        "1. Retrieve Country details by Name\n"+
                        "2. Retrieve Country details by Code\n");
                String options = scanner.next();
                continueRunningApplication(options);
            } else {
                logger.info("Enter either Y or N");
            }
        }
    }

    private void continueRunningApplication(String options) {
        String input = "";
        switch(options)
        {
            case "1":
                logger.info("\nEnter full or partial name of the Country");
                input = scanner.next();
                callCountryService("name",input);
                break;
            case "2":
                logger.info("\nEnter one or more country codes delimited by ;");
                input = scanner.next();
                callCountryService("codes",input);
                break;
            default:
                logger.info("\nEnter either 1 or 2 to continue or N to exit");
                input = scanner.next();
                if(input.equalsIgnoreCase("n")) {
                    logger.info("\nExiting the application");
                    System.exit(0);
                } else {
                    continueRunningApplication(input);
                }
        }
    }

    private void callCountryService(String serviceName, String input) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(getCountryServiceUri(serviceName,input));
            HttpResponse httpresponse = httpclient.execute(httpget);
            int statusCode = httpresponse.getStatusLine().getStatusCode();
            logger.info("StatusCode -> " + statusCode);
            if(statusCode == 200) {
                HttpEntity entity = httpresponse.getEntity();
                if (entity != null) {
                    String response = EntityUtils.toString(entity);
                    logger.info(response);
                    getCapitalOfCountry(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCapitalOfCountry(String response) {
        try {
            Object object = new JSONTokener(response).nextValue();
            if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.get(i)));
                    extractValueWithJsonPath(jsonObject, CAPITAL_PATH);
                }
            } else if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(response);
                extractValueWithJsonPath(jsonObject, CAPITAL_PATH);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getCountryServiceUri(String serviceName, String input) {
        String countryServiceUri = "";
        if(serviceName.equalsIgnoreCase("name")) {
            countryServiceUri = REST_COUNTRIES_URL + NAME_ENDPOINT + input;
        } else if(serviceName.equalsIgnoreCase("codes")) {
            if(input.contains(";")){
                countryServiceUri = REST_COUNTRIES_URL + CODES_ENDPOINT + input;
            } else {
                countryServiceUri = REST_COUNTRIES_URL + CODE_ENDPOINT + input;
            }
        }
        return countryServiceUri;
    }

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
