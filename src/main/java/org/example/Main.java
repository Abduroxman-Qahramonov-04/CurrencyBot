package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest =HttpRequest.newBuilder(URI.create("https://cbu.uz/oz/arkhiv-kursov-valyut/json/USD/2023-01-01/"))
                .GET().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        String json = httpResponse.body();
        Type type = new TypeToken<ArrayList<CurrencyAndInfo>>(){}.getType();
        System.out.println(json);
        ArrayList<CurrencyAndInfo> currencies = gson.fromJson(json, type);
        currencies.forEach(System.out::println);
        System.out.println(currencies.get(0).getRate());


    }
}