package com.github.arteam.random.org.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RandomOrgTest {

    HttpServer httpServer;

    @Before
    public void init() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode spec = objectMapper.readTree(RandomOrgTest.class.getResource("/requests.json"));

        httpServer = HttpServer.create(new InetSocketAddress(8365), 0);
        httpServer.createContext("/", httpExchange -> {
            Assert.assertEquals(httpExchange.getRequestMethod(), "POST");
            try {
                JsonNode jsonRequest = objectMapper.readTree(httpExchange.getRequestBody());
                String method = jsonRequest.get("method").asText();
                JsonNode jsonRequestResponse = spec.get(method);
                Assert.assertEquals(jsonRequestResponse.get("request"), jsonRequest);

                httpExchange.sendResponseHeaders(200, 0);
                objectMapper.writeValue(httpExchange.getResponseBody(), jsonRequestResponse.get("response"));
            } finally {
                httpExchange.close();
            }
        });
        httpServer.start();
    }

    @After
    public void tearDown() throws Exception {
        httpServer.stop(0);
    }

    @Test
    public void testGenerateIntegers() throws Exception {
        RandomOrg randomOrg = new RandomOrg("6b1e65b9-4186-45c2-8981-b77a9842c4f0",
                new URI("http://127.0.0.1:8365"), () -> 42L);
        List<Integer> randomIntegers = randomOrg.generateIntegers(6, 1, 6, true, null);
        System.out.println(randomIntegers);
        Assert.assertEquals(randomIntegers, Arrays.asList(1, 5, 4, 6, 6, 4));
    }
}