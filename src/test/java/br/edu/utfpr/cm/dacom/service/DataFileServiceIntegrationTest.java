package br.edu.utfpr.cm.dacom.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.cm.dacom.entity.DataFile;
import br.edu.utfpr.cm.dacom.exception.AnyPersistenceException;
import br.edu.utfpr.cm.dacom.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cm.dacom.service.DataFileService;

@SpringBootTest
public class DataFileServiceIntegrationTest {
	
	@Autowired
    DataFileService dataFileService;
	
	
	@DisplayName("Testar a criação de um DataFile no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{
		dataFileService.create(DataFile.builder()
									   .nome("AAA")
									   .build());
	
		assertThat(dataFileService.readAll().get(0).getNome()).isEqualTo("AAA");
	}
	
	@DisplayName("Testar a criação inválida de um DataFile com nome duplicado no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate_Duplicado() throws Exception{
		dataFileService.create(DataFile.builder()
									   .nome("AAA")
									   .build());
		
		assertThatThrownBy(() -> {dataFileService.create(DataFile.builder()
											   					 .nome("AAA")
											   					 .build());
		}).isInstanceOf(EntityAlreadyExistsException.class);
	}
	
	@DisplayName("Testar a criação passando um parâmetro null.")
	@Test
	@Transactional
	@Rollback
	void testCreate_Null() throws Exception{
		assertThatThrownBy(() -> {dataFileService.create(null);
		}).isInstanceOf(AnyPersistenceException.class);
	}
	
	
	@DisplayName("Testar a criação inválida de um DataFile sem dados no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate_Vazio() throws Exception{
		assertThatThrownBy(() -> {dataFileService.create(DataFile.builder()
											   					 .build());
		}).isInstanceOf(AnyPersistenceException.class);
	}
}
