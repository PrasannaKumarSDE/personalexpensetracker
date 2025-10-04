package com.example.expensetracker.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.expensetracker.model.expense;
import com.example.expensetracker.service.expenseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class expenseController {

    private final expenseService service;

    public expenseController(expenseService service) {
        this.service = service;
    }

    // --- Web UI endpoints (Thymeleaf)
    @GetMapping("/")
    public String index(Model model) {
        List<expense> expenses = service.findAll();
        model.addAttribute("expenses", expenses);
        model.addAttribute("summaryByCategory", service.getSummaryByCategory());
        model.addAttribute("summaryByMonth", service.getSummaryByMonth());
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("expense", new expense());
        return "add";
    }

    @PostMapping("/add")
    public String addSubmit(@Valid @ModelAttribute("expense") expense expense, BindingResult br) {
        if (br.hasErrors()) {
            return "add";
        }
        service.save(expense);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        expense e = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid expense id: " + id));
        model.addAttribute("expense", e);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id, @Valid @ModelAttribute expense expense, BindingResult br) {
        if (br.hasErrors()) {
            return "edit";
        }
        expense.setId(id);
        service.save(expense);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/";
    }

    // --- REST API endpoints (JSON)
    @RestController
    @RequestMapping("/api/expenses")
    public static class ApiController {
        private final expenseService service;
        public ApiController(expenseService service) { this.service = service; }

        @GetMapping
        public List<expense> getAll() { return service.findAll(); }

        @GetMapping("/{id}")
        public expense getById(@PathVariable Long id) {
            return service.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        }

        @PostMapping
        public expense create(@Valid @RequestBody expense expense) { return service.save(expense); }

        @PutMapping("/{id}")
        public expense update(@PathVariable Long id, @Valid @RequestBody expense expense) {
            expense.setId(id);
            return service.save(expense);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) { service.deleteById(id); }

        @GetMapping("/summary/category")
        public Map<String, Double> summaryByCategory() { return service.getSummaryByCategory(); }

        @GetMapping("/summary/month")
        public Map<String, Double> summaryByMonth() { return service.getSummaryByMonth(); }

        @GetMapping("/filter")
        public List<expense> filter(
                @RequestParam(required = false) String category,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
            if (category != null) return service.findByCategory(category);
            if (start != null && end != null) return service.findByDateRange(start, end);
            return service.findAll();
        }
    }
}