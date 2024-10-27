package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.Calculation;
import org.springframework.stereotype.Repository;

@Repository
public class StringCalculatorRepository {
    private final List<Calculation> calculations = new ArrayList<>();

    public void save(Calculation calculation) {
        calculations.add(calculation);
    }

    public List<Calculation> findAll() {
        return calculations;
    }
}
