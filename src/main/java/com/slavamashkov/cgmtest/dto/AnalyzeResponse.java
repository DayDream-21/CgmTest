package com.slavamashkov.cgmtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeResponse {
    private Character character;
    private Integer charFrequency;
    private Integer maxContinuousSequence;
}
