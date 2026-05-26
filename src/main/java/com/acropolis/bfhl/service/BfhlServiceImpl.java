package com.acropolis.bfhl.service;

import com.acropolis.bfhl.dto.BfhlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BfhlServiceImpl implements BfhlService {

    // ── Replace these constants with your own details ──────────────────────
    private static final String USER_ID     = "kuldeep_kelde_29042006";
    private static final String EMAIL       = "kuldeepkelde231154@acropolis.in";
    private static final String ROLL_NUMBER = "0827CI231071";
    // ───────────────────────────────────────────────────────────────────────

    @Override
    public BfhlResponse processData(List<String> data) {
        log.debug("Processing {} elements", data.size());

        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabeticElems  = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long         numericSum       = 0;

        for (String element : data) {
            if (isNumber(element)) {
                long val = Long.parseLong(element);
                numericSum += val;
                if (val % 2 == 0) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }
            } else if (isAlphabetic(element)) {
                alphabeticElems.add(element);
            } else {
                specialChars.add(element);
            }
        }

        // Build uppercase alphabets list
        List<String> alphabetsUpper = alphabeticElems.stream()
                .map(String::toUpperCase)
                .toList();

        String concatString = alphabeticElems.isEmpty()
                ? ""
                : buildConcatString(alphabeticElems);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabetsUpper)
                .specialCharacters(specialChars)
                .sum(String.valueOf(numericSum))
                .concatString(concatString)
                .build();
    }

    // ── Helper methods ──────────────────────────────────────────────────────

    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        int start = 0;
        if (s.charAt(0) == '-') {
            if (s.length() == 1) return false;
            start = 1;
        }
        for (int i = start; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    private boolean isAlphabetic(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the concat_string from alphabetic elements:
     * 1. Flatten all chars from each element (preserving order)
     * 2. Concatenate into one string
     * 3. Reverse it
     * 4. Apply alternating caps (index 0 = uppercase)
     */
    private String buildConcatString(List<String> alphabeticElements) {
        StringBuilder sb = new StringBuilder();
        for (String elem : alphabeticElements) {
            sb.append(elem);
        }
        String reversed = sb.reverse().toString();
        return applyAlternatingCaps(reversed);
    }

    private String applyAlternatingCaps(String input) {
        StringBuilder result = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            result.append(i % 2 == 0
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c));
        }
        return result.toString();
    }
}
