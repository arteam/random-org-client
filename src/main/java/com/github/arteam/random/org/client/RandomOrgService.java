package com.github.arteam.random.org.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arteam.random.org.client.domain.RandomOrgResult;
import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.builder.RequestBuilder;
import com.github.arteam.simplejsonrpc.client.generator.CurrentTimeIdGenerator;
import com.github.arteam.simplejsonrpc.client.generator.IdGenerator;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcOptional;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 1/15/15
 * Time: 12:18 AM
 *
 * @author Artem Prigoda
 */
@JsonRpcService
interface RandomOrgService {

    @JsonRpcMethod("generateIntegers")
    RandomOrgResult<Integer> generateIntegers(@JsonRpcParam("apiKey") String apiKey, @JsonRpcParam("n") int n,
                                              @JsonRpcParam("min") int min, @JsonRpcParam("max") int max,
                                              @JsonRpcOptional @JsonRpcParam("replacement") Boolean replacement,
                                              @JsonRpcOptional @JsonRpcParam("base") Integer base);

    @JsonRpcMethod("generateDecimalFractions")
    RandomOrgResult<Double> generateDecimalFractions(@JsonRpcParam("apiKey") String apiKey, @JsonRpcParam("n") int n,
                                                     @JsonRpcParam("decimalPlaces") int decimalPlaces,
                                                     @JsonRpcOptional @JsonRpcParam("replacement") Boolean replacement,
                                                     @JsonRpcOptional @JsonRpcParam("base") Integer base);

    @JsonRpcMethod("generateGaussians")
    RandomOrgResult<Double> generateGaussians(@JsonRpcParam("apiKey") String apiKey, @JsonRpcParam("n") int n,
                                              @JsonRpcParam("mean") double mean,
                                              @JsonRpcParam("standardDeviation") double standardDeviation,
                                              @JsonRpcParam("significantDigits") int significantDigits);

    @JsonRpcMethod("generateStrings")
    RandomOrgResult<String> generateStrings(@JsonRpcParam("apiKey") String apiKey, @JsonRpcParam("n") int n,
                                            @JsonRpcParam("length") int length,
                                            @JsonRpcParam("characters") String characters,
                                            @JsonRpcOptional @JsonRpcParam("replacement") Boolean replacement);

    @JsonRpcMethod("generateUUIDs")
    RandomOrgResult<String> generateUUIDs(@JsonRpcParam("apiKey") String apiKey, @JsonRpcParam("n") int n);
}
