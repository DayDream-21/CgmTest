package com.slavamashkov.cgmtest.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.slavamashkov.cgmtest.dto.AnalyzeResponse;
import com.slavamashkov.cgmtest.dto.Stat;
import com.slavamashkov.cgmtest.service.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("/{string}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    public List<AnalyzeResponse> analyze(@PathVariable(name = "string") String string) {
        List<AnalyzeResponse> result = new ArrayList<>();

        Map<Character, Integer> charFrequency = analyzeService.calculateCharFrequency(string);

        Map<Character, Integer> maxContinuousSequence = analyzeService.calculateMaxContinuousSequence(string);

        Set<Character> setOfCharInString = string.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        setOfCharInString.stream()
                .map(character -> AnalyzeResponse.builder()
                    .character(character)
                    .charFrequency(charFrequency.get(character))
                    .maxContinuousSequence(maxContinuousSequence.get(character))
                    .build()
                ).forEach(analyzeResponse -> {
                    analyzeService.sendStat(Stat.builder()
                            .analyzeResponse(analyzeResponse)
                            .queryLength(string.length())
                            .build());

                    result.add(analyzeResponse);
                });

        return result;
    }
}