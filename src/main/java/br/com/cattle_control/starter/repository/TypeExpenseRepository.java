package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.TypeExpense;

public interface TypeExpenseRepository extends JpaRepository<TypeExpense, Integer> {
    
}
