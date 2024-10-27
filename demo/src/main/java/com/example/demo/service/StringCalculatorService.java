package com.example.demo.service;

import com.example.demo.repository.StringCalculatorRepository;
import com.example.demo.domain.Calculation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class StringCalculatorService {

    private final StringCalculatorRepository repository;
    private int callCount = 0;

    public StringCalculatorService(StringCalculatorRepository repository) {
        this.repository = repository;
    }

    public int add(String numbers) throws Exception {
        callCount++;
        if (numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",|\n";
        if (numbers.startsWith("//")) {
            int delimiterEnd = numbers.indexOf("\n");
            delimiter = buildCustomDelimiter(numbers.substring(2, delimiterEnd));
            numbers = numbers.substring(delimiterEnd + 1);
        }

        String[] tokens = numbers.split(delimiter);
        int sum = 0;
        List<Integer> negatives = new ArrayList<>();

        for (String token : tokens) {
            if (!token.isEmpty()) {
                int number = Integer.parseInt(token);
                if (number < 0) {
                    negatives.add(number);
                } else if (number <= 1000) {
                    sum += number;
                }
            }
        }

        if (!negatives.isEmpty()) {
            throw new Exception("Negatives not allowed: " + negatives);
        }

        Calculation calculation = new Calculation(numbers, sum);
        repository.save(calculation);

        return sum;
    }

    private String buildCustomDelimiter(String delimiterPart) {
        if (delimiterPart.startsWith("[")) {
            String[] delimiters = delimiterPart.split("\\[|\\]");
            return String.join("|", Arrays.stream(delimiters)
                    .filter(d -> !d.isEmpty())
                    .map(Pattern::quote) // Escape special characters
                    .toArray(String[]::new));
        }
        return Pattern.quote(delimiterPart); // Escape if single character delimiter
    }


    public int getCallCount() {
        return callCount;
    }
}
