package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    
}
