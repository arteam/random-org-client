package com.github.arteam.random.org.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date: 1/13/15
 * Time: 11:10 PM
 *
 * @author Artem Prigoda
 */
public class RandomOrgResult {

    private final RandomInfo random;

    private final int bitsUsed;

    private final int bitsLeft;

    private final int requestsLeft;

    private final int advisoryDelay;

    public RandomOrgResult(@JsonProperty("random") RandomInfo random, @JsonProperty("bitsUsed") int bitsUsed,
                           @JsonProperty("bitsLeft") int bitsLeft, @JsonProperty("requestsLeft") int requestsLeft,
                           @JsonProperty("advisoryDelay") int advisoryDelay) {
        this.random = random;
        this.bitsUsed = bitsUsed;
        this.bitsLeft = bitsLeft;
        this.requestsLeft = requestsLeft;
        this.advisoryDelay = advisoryDelay;
    }

    public RandomInfo getRandom() {
        return random;
    }

    public int getBitsUsed() {
        return bitsUsed;
    }

    public int getBitsLeft() {
        return bitsLeft;
    }

    public int getRequestsLeft() {
        return requestsLeft;
    }

    public int getAdvisoryDelay() {
        return advisoryDelay;
    }

    @Override
    public String toString() {
        return "RandomOrgResult{" +
                "random=" + random +
                ", bitsUsed=" + bitsUsed +
                ", bitsLeft=" + bitsLeft +
                ", requestsLeft=" + requestsLeft +
                ", advisoryDelay=" + advisoryDelay +
                '}';
    }
}
