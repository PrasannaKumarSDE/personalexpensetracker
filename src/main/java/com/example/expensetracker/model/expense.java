package com.example.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Size(max = 255)
    private String note;

    @Size(max = 50)
    private String category;

    public expense() {}

    public expense(Double amount, LocalDate date, String note, String category) {
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}