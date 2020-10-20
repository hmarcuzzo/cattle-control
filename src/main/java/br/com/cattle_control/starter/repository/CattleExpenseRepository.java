package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.CattleExpense;

public interface CattleExpenseRepository extends JpaRepository<CattleExpense, Integer> {
    
}
