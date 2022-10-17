package ru.bigint.parser.houses.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class ParserHttpClient {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static String sendGet(String url, Map<String, String> params) {
        String encodedUrl = url + "?" + getParamsUrlEncoded(params);
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .uri(URI.create(encodedUrl))
                .setHeader("User-Agent", "Chrome")
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return urlEncoded;
    }
}
