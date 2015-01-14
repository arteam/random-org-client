package com.github.arteam.random.org.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Date: 1/13/15
 * Time: 11:10 PM
 *
 * @author Artem Prigoda
 */
public class RandomInfo {

    private final List<Integer> data;

    private final Date completionTime;

    public RandomInfo(@JsonProperty("data") List<Integer> data,
                      @JsonProperty("completionTime") Date completionTime) {
        this.data = data;
        this.completionTime = completionTime;
    }

    public List<Integer> getData() {
        return data;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    @Override
    public String toString() {
        return "RandomInfo{" +
                "data=" + data +
                ", completionTime=" + completionTime +
                '}';
    }
}
