package br.edu.utfpr.cm.dacom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.cm.dacom.entity.DataFile;

public interface DataFileRepository extends JpaRepository<DataFile, Long> {
    
}
	