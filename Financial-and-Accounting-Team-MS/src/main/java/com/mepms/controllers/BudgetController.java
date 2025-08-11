package com.mepms.controllers;


import com.mepms.entity.Budget;
import com.mepms.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public Budget getCurrentBudget() {
        return budgetService.getCurrentYearBudget();
    }

    @PostMapping
    public Budget setBudget(@RequestBody Budget budget) {
        return budgetService.setCurrentYearBudget(
            budget.getAmount(),
            budget.getCurrency(),
            budget.getNotes()
            
        );
    }
    
    @PutMapping
    public Budget updateBudget(@RequestBody Budget budget) {
        return budgetService.updateBudget(budget);
    }
}
