package com.slavamashkov.cgmtest.service;

import com.slavamashkov.cgmtest.controller.StatisticController;
import com.slavamashkov.cgmtest.dto.Stat;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyzeService {
    public Map<Character, Integer> calculateCharFrequency(String string) {
        Map<Character, Integer> charCount = new HashMap<>();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (charCount.containsKey(c)) {
                charCount.put(c, charCount.get(c) + 1);
            } else {
                charCount.put(c, 1);
            }
        }

        return charCount;
    }

    public Map<Character, Integer> calculateMaxContinuousSequence(String str) {
        HashMap<Character, Integer> seqLength = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int currentSeqLength = 1;

            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(j) == ch) {
                    currentSeqLength++;
                } else {
                    break;
                }
            }

            if (seqLength.containsKey(ch)) {
                int prevSeqLength = seqLength.get(ch);
                seqLength.put(ch, Math.max(prevSeqLength, currentSeqLength));
            } else {
                seqLength.put(ch, currentSeqLength);
            }
        }

        return seqLength;
    }

    public void sendStat(Stat stat) {
        StatisticController.receiveStat(stat);
    }
}
