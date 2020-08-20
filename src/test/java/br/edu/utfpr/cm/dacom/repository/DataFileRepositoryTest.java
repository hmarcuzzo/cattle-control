package br.edu.utfpr.cm.dacom.repository;


import static org.assertj.core.api.Assertions.*;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.utfpr.cm.dacom.entity.DataFile;


@DataJpaTest
public class DataFileRepositoryTest {
	
	@Autowired
	DataFileRepository dataFileRepository;
	
	
	@DisplayName("Testar injeção de dependencia do repositório.")
	@Test
	void testInjectedComponents_NotNull() {
		assertThat(dataFileRepository).isNotNull();
	}
	
	
	@DisplayName("Testar a inserção com dados parciais, permitida.")
	@Test
	void testSave_Parcial() {
		dataFileRepository.save(DataFile.builder()
											.nome("Arquivo 1")									
											.build()				
				);
		
		assertThat(dataFileRepository.count()).isEqualTo(1);
	}
	
	@DisplayName("Testar a inserção com imcompletos, portanto, não permitida.")
	@Test
	void testSave_Incommpleto() {
		
		assertThatThrownBy(() -> {
			dataFileRepository.save(DataFile.builder()
											.descricao("Arquivo 1")									
											.build());
		}).isInstanceOf(ConstraintViolationException.class);

	}
	
	
	@DisplayName("Testar a inserção com dados completos.")
	@Test
	void testSave_Complete() {
		DataFile f = DataFile.builder()
							 .nome("Arquivo 1")
							 .descricao("Descrição 1")
							 .build();
		f.setNome("aaa.jpg");
		f.setArquivoAnexo("Conteúdo Arquivo".getBytes());
		
		dataFileRepository.save(f);
		
		DataFile fSaved = dataFileRepository.findAll().get(0);
		
		assertThat(f.getId()).isEqualTo(fSaved.getId());
		assertThat(f.getNome()).isEqualTo(fSaved.getNome());
		assertThat(f.getDescricao()).isEqualTo(fSaved.getDescricao());
		assertThat(f.getNomeArquivoAnexo()).isEqualTo(fSaved.getNomeArquivoAnexo());
		assertThat(f.getArquivoAnexo()).isEqualTo(fSaved.getArquivoAnexo());
	}
}
