package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Data
public class CurrencyAndInfo {
    private String id;
    private String Ccy;
    private String CcyNm_RU;
    private String CcyNm_UZ;
    private String CcyNm_UZC;
    private String CcyNm_EN;
    private String Nominal;
    private String Rate;
    private String Diff;
    private String Date;

    public CurrencyAndInfo generateCurrency(String uri) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest =HttpRequest.newBuilder(URI.create(uri))
                .GET().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        String json = httpResponse.body();
        Type type = new TypeToken<ArrayList<CurrencyAndInfo>>(){}.getType();
        ArrayList<CurrencyAndInfo> currencies = gson.fromJson(json, type);
        System.out.println(json);
        return currencies.get(0);
    }

}
