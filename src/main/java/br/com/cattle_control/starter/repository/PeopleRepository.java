package br.com.cattle_control.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cattle_control.starter.model.People;

public interface PeopleRepository extends JpaRepository<People, Long> {
    
}