package com.lcv.elverapi.util;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class ElverHttpRequest {
    public static HttpResponse<String> makeRequest(String url, Consumer<HttpRequest.Builder> builderConsumer) throws IOException, InterruptedException {
        URI uri = URI.create(url);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(uri)
                .header("accept", "application/json");

        if (builderConsumer != null) builderConsumer.accept(requestBuilder);

        HttpRequest request = requestBuilder.build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;

        // make up to 3 requests
        for (int i = 0; i < 3; i++) {
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                if (i == 2) {
                    throw e;
                }

                Thread.sleep(500);
            }
        }

        return response;

    }
}
