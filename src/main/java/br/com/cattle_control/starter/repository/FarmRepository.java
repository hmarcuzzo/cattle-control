package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.Farm;

public interface FarmRepository extends JpaRepository<Farm, Integer> {
    
}
