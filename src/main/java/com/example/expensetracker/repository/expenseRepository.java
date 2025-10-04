package com.example.expensetracker.repository;

import com.example.expensetracker.model.expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface expenseRepository extends JpaRepository<expense, Long> {
    List<expense> findByCategory(String category);
    List<expense> findByDateBetween(LocalDate start, LocalDate end);
    @Query("SELECT e.category, SUM(e.amount) FROM expense e GROUP BY e.category")
    List<Object[]> sumByCategory();


    @Query("SELECT FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date), SUM(e.amount) FROM expense e GROUP BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date)")
    List<Object[]> sumByMonth();
}

