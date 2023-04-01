package com.slavamashkov.cgmtest.controller;

import com.slavamashkov.cgmtest.dto.AnalyzeResponse;
import com.slavamashkov.cgmtest.dto.Stat;
import com.slavamashkov.cgmtest.dto.StatisticResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    private static Map<Character, List<Stat>> responseMap = new HashMap<>();

    @GetMapping
    public List<StatisticResponse> getStat() {
        return responseMap.keySet()
                .stream()
                .map(character -> StatisticResponse.builder()
                        .character(character)
                        .numberOfEncountersOfCharInQueries(responseMap.get(character)
                                .size()
                        )
                        .avgLengthOfQueryWithThisChar(responseMap.get(character)
                                .stream()
                                .mapToInt(Stat::getQueryLength)
                                .average()
                                .orElse(0.0)
                        )
                        .avgLengthOfMaxContSeqInQueryWithThisChar(responseMap.get(character)
                                .stream()
                                .map(Stat::getAnalyzeResponse)
                                .map(AnalyzeResponse::getMaxContinuousSequence)
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0.0)
                        )
                        .build())
                .collect(Collectors.toList());
    }

    public static void receiveStat(Stat stat) {
        Character character = stat.getAnalyzeResponse().getCharacter();

        if (responseMap.get(character) == null) {
            List<Stat> list = new ArrayList<>();

            list.add(stat);

            responseMap.put(character, list);
        } else {
            responseMap.get(character).add(stat);
        }
    }
}
