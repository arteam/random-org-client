package com.github.arteam.random.org.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arteam.random.org.client.domain.RandomOrgResult;
import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.builder.RequestBuilder;
import com.github.arteam.simplejsonrpc.client.generator.CurrentTimeIdGenerator;
import com.github.arteam.simplejsonrpc.client.generator.IdGenerator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 1/15/15
 * Time: 11:53 PM
 *
 * @author Artem Prigoda
 */
public class RandomOrg {

    private static final URI RANDOM_ORG_GATEWAY = URI.create("https://api.random.org/json-rpc/1/invoke");
    private static final SimpleDateFormat RANDOM_ORG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private final String apiKey;
    private CloseableHttpClient httpClient;
    private RandomOrgService service;
    private URI gateway = RANDOM_ORG_GATEWAY;
    private IdGenerator<Long> idGenerator = new CurrentTimeIdGenerator();

    public RandomOrg(String apiKey) {
        this.apiKey = apiKey;
        start();
    }

    RandomOrg(String apiKey, URI gateway, IdGenerator<Long> idGenerator) {
        this.apiKey = apiKey;
        this.gateway = gateway;
        this.idGenerator = idGenerator;
        start();
    }

    private void start() {
        httpClient = HttpClients.createDefault();
        service = new JsonRpcClient(request -> {
            System.out.println("Request: " + request);
            HttpPost httpPost = new HttpPost(gateway);
            httpPost.setEntity(new StringEntity(request, StandardCharsets.UTF_8));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String data = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + data);
                return data;
            }
        }, new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDateFormat(RANDOM_ORG_DATE_FORMAT))
                .onDemand(RandomOrgService.class, idGenerator);
    }

    @NotNull
    public List<Integer> generateIntegers(int amount, int min, int max) {
        return service.generateIntegers(apiKey, amount, min, max, null, null)
                .getRandom().getData();
    }

    @NotNull
    public List<Integer> generateIntegers(int amount, int min, int max,
                                          @Nullable Boolean replacement, @Nullable Integer base) {
        return service.generateIntegers(apiKey, amount, min, max, replacement, base)
                .getRandom().getData();
    }

    public List<Double> generateDecimalFractions(int amount, int decimalPlaces) {
        return service.generateDecimalFractions(apiKey, amount, decimalPlaces, null, null)
                .getRandom().getData();
    }

    public List<Double> generateDecimalFractions(int amount, int decimalPlaces,
                                                 @Nullable Boolean replacement, @Nullable Integer base) {
        return service.generateDecimalFractions(apiKey, amount, decimalPlaces, replacement, base)
                .getRandom().getData();
    }

    public List<Double> generateGaussians(int amount, double mean, double standardDeviation, int significantDigits) {
        return service.generateGaussians(apiKey, amount, mean, standardDeviation, significantDigits)
                .getRandom().getData();
    }

    public List<String> generateStrings(int amount, int length) {
        return service.generateStrings(apiKey, amount, length, ALPHABET, null).getRandom().getData();
    }

    public List<String> generateUUIDs(int amount) {
        return service.generateUUIDs(apiKey, amount).getRandom().getData();
    }

    public void stop() {
        try {
            httpClient.close();
        } catch (IOException e) {
            throw new IllegalStateException("Unable close http client", e);
        }
    }
}
