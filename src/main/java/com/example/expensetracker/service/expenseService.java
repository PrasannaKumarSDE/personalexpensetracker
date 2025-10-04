package com.example.expensetracker.service;


import org.springframework.stereotype.Service;

import com.example.expensetracker.model.expense;
import com.example.expensetracker.repository.expenseRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class expenseService {

    private final expenseRepository repository;

    public expenseService(expenseRepository repository) {
        this.repository = repository;
    }

    public expense save(expense expense) {
        return repository.save(expense);
    }

    public Optional<expense> findById(Long id) {
        return repository.findById(id);
    }

    public List<expense> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<expense> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<expense> findByDateRange(LocalDate start, LocalDate end) {
        return repository.findByDateBetween(start, end);
    }

    public Map<String, Double> getSummaryByCategory() {
        List<Object[]> rows = repository.sumByCategory();
        Map<String, Double> map = new HashMap<>();
        for (Object[] r : rows) {
            String cat = (String) r[0];
            Double sum = (Double) r[1];
            map.put(cat == null ? "Uncategorized" : cat, sum);
        }
        return map;
    }

    public Map<String, Double> getSummaryByMonth() {
        List<Object[]> rows = repository.sumByMonth();
        Map<String, Double> map = new TreeMap<>();
        for (Object[] r : rows) {
            Integer year = (Integer) r[0];
            Integer month = (Integer) r[1];
            Double sum = (Double) r[2];
            String key = String.format("%04d-%02d", year, month);
            map.put(key, sum);
        }
        return map;
    }
}