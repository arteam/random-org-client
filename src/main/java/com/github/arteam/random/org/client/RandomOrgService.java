package com.github.arteam.random.org.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arteam.random.org.client.domain.RandomOrgResult;
import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.generator.CurrentTimeIdGenerator;
import com.github.arteam.simplejsonrpc.client.generator.IdGenerator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 1/15/15
 * Time: 12:18 AM
 *
 * @author Artem Prigoda
 */
public class RandomOrgService {

    private static final URI RANDOM_ORG_GATEWAY = URI.create("https://api.random.org/json-rpc/1/invoke");
    private static final SimpleDateFormat RANDOM_ORG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String apiKey;
    private CloseableHttpClient httpClient;
    private JsonRpcClient jsonRpcClient;
    private IdGenerator<Long> idGenerator = new CurrentTimeIdGenerator();

    public RandomOrgService(String apiKey) {
        this.apiKey = apiKey;
        start();
    }

    private void start() {
        httpClient = HttpClients.createDefault();
        jsonRpcClient = new JsonRpcClient(request -> {
            System.out.println("Request: " + request);
            HttpPost httpPost = new HttpPost(RANDOM_ORG_GATEWAY);
            httpPost.setEntity(new StringEntity(request, StandardCharsets.UTF_8));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String data = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + data);
                return data;
            }
        }, new ObjectMapper().setDateFormat(RANDOM_ORG_DATE_FORMAT));
    }

    public List<Integer> generate(int amount, int min, int max) {
        RandomOrgResult response = jsonRpcClient.createRequest()
                .id(idGenerator.generate())
                .method("generateIntegers")
                .param("apiKey", apiKey)
                .param("n", amount)
                .param("min", min)
                .param("max", max)
                .returnAs(RandomOrgResult.class)
                .execute();
        return response != null ? response.getRandom().getData() : new ArrayList<>();
    }

    public void stop() {
        try {
            httpClient.close();
        } catch (IOException e) {
            throw new IllegalStateException("Unable close http client", e);
        }
    }
}
