package com.slavamashkov.cgmtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
    private Character character;
    private Integer numberOfEncountersOfCharInQueries;
    private Double avgLengthOfQueryWithThisChar;
    private Double avgLengthOfMaxContSeqInQueryWithThisChar;
}
