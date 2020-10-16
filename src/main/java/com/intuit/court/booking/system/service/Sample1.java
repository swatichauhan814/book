package com.intuit.court.booking.system.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Sample1 {

    public static void main(String[] args) {

        String scores[][] = {{"Ram","155"},
                {"Shyam","145"},
                {"Ram","156"},
                {"Balram","159"},
                {"Balram","150"},
                {"Ram","135"},
                {"Mira","156"},
                {"Mira","152"},
                {"Shyam","155"}};

        Map<Object, Double> scoreMap = Arrays.stream(scores)
                .collect(Collectors.groupingBy(i -> i[0], Collectors.averagingInt(i -> Integer.parseInt(i[1]))));


        Map<String, Double> collect = Arrays.stream(scores)
                .collect(Collectors.groupingBy(i -> i[0], Collectors.averagingInt(i -> Integer.parseInt(i[1]))));

        String winner = scoreMap.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .get().getKey().toString();

        System.out.println(collect);

        String key = collect.entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .get().getKey();

        System.out.println(key);
    }
}
