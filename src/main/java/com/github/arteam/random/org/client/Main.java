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
        RandomOrg randomOrg = new RandomOrg(apiKey);
        List<Integer> randomIds = randomOrg.generateIntegers(10, 0, 100);
        System.out.println(randomIds);
        List<Integer> extendedRandomIds = randomOrg.generateIntegers(10, 0, 100, false, 10);
        System.out.println(extendedRandomIds);
        List<Double> randomDoubles = randomOrg.generateDecimalFractions(10, 4);
        System.out.println(randomDoubles);
    }
}
