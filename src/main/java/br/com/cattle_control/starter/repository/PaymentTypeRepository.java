package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
    
}
