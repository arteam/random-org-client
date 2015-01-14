package com.github.arteam.random.org.client;

import java.util.List;

/**
 * Date: 1/13/15
 * Time: 10:31 PM
 *
 * @author Artem Prigoda
 */
public class Main {

    public static void main(String[] args) {

        String apiKey = System.getProperty("api.key");
        RandomOrgService randomOrgService = new RandomOrgService(apiKey);
        List<Integer> randomIds = randomOrgService.generate(10, 0, 100);
        System.out.println(randomIds);
        List<Double> randomDoubles = randomOrgService.generateDecimalFractions(10, 4);
        System.out.println(randomDoubles);
    }
}
