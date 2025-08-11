package com.mepms.service.impl;

import com.mepms.entity.Budget;
import com.mepms.repository.BudgetRepository;
import com.mepms.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget getCurrentYearBudget() {
        int year = Year.now().getValue();
        Budget budget = budgetRepository.findByYear(year);
        if (budget == null) {
            // Optionally, create an initial budget
            budget = new Budget();
            budget.setYear(year);
            budget.setAmount(0);
            budget.setRemaining(0);
            budget.setSpent(0);
            budget.setCurrency("INR");
            budget.setLastUpdated(LocalDateTime.now());
            budgetRepository.save(budget);
        }
        return budget;
    }

    @Override
    public Budget setCurrentYearBudget(double amount, String currency, String notes) {
        int year = Year.now().getValue();
        Budget budget = budgetRepository.findByYear(year);
        if (budget == null) {
            budget = new Budget();
            budget.setYear(year);
        }
        budget.setAmount(amount);
        budget.setRemaining(0);
        budget.setSpent(0);
        budget.setCurrency(currency);
        budget.setNotes(notes);
        budget.setLastUpdated(LocalDateTime.now());
        return budgetRepository.save(budget);
    }

    @Override
    public Budget updateBudget(Budget budget) {
        budget.setLastUpdated(LocalDateTime.now());
        return budgetRepository.save(budget);
    }
}
