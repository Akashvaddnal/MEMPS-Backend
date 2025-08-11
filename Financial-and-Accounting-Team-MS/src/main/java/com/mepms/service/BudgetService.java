package com.mepms.service;

import java.util.List;

import com.mepms.entity.Budget;

public interface BudgetService {
    Budget getCurrentYearBudget();
    Budget setCurrentYearBudget(double amount, String currency, String notes);
    Budget updateBudget(Budget budget);
  
}
