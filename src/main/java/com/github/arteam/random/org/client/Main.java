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
        System.out.println("Random integers:" + randomIds);

        List<Integer> extendedRandomIds = randomOrg.generateIntegers(10, 0, 100, false, 10);
        System.out.println("Repeated random integers:" + extendedRandomIds);

        List<Double> randomDoubles = randomOrg.generateDecimalFractions(10, 4);
        System.out.println(randomDoubles);
        System.out.println("Random doubles:" + randomDoubles);

        List<Double> randomGaussians = randomOrg.generateGaussians(10, 5.0, 0.5, 2);
        System.out.println("Random gaussians:" + randomGaussians);

        List<String> randomStrings = randomOrg.generateStrings(10, 8);
        System.out.println("Random strings: " + randomStrings);
    }
}
