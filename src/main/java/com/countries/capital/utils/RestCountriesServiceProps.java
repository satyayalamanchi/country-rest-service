package com.countries.capital.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RestCountriesServiceProps {

    @Value("${restCountries.service.url}")
    private String restCountriesServiceUrl;

    @Value("${name.endPoint}")
    private String nameEndPoint;

    @Value("${code.endPoint}")
    private String codeEndPoint;

    @Value("${codes.endPoint}")
    private String codesEndPoint;

    @Value("${capital.jsonPath}")
    String capitalJsonPath;

    public String getNameUri(){
        return restCountriesServiceUrl + nameEndPoint;
    }

    public String getCodeUri(){
        return restCountriesServiceUrl + codeEndPoint;
    }

    public String getCodesUri(){
        return restCountriesServiceUrl + codesEndPoint;
    }

    public String getRestCountriesUriForCodes(String codes) {
        String uri = "";
        if(codes.contains(",") ) {
            codes.replace(",",";");
            uri = getCodesUri() + codes;
        } else if(codes.contains(";")){
            uri = getCodesUri() + codes;
        } else {
            uri = getCodeUri() + codes;
        }
        return uri;
    }
}
