package br.com.cattle_control.starter.service;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.Farm;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FarmServiceIntegrationTest {

    @Autowired
    FarmService farmService;


    @DisplayName("Testar a criação de uma Fazenda no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(false)
                                .build();

        farmService.create(farm);

        assertThat(farmService.readAll().get(0).getName()).isEqualTo("Fazenda Teste");
    }


    @DisplayName("Testar a procura de uma Fazenda por Número de Registro.")
	@Test
	@Transactional
	@Rollback
	void testFindByRegisterNumber() throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(false)
                                .build();

        farmService.create(farm);

        
        assertThat(farmService.findByRegisterNumber("9999999-9")).isEqualTo(farm);
    }

    @DisplayName("Testar o update de um Local no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdatePlace () throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(false)
                                .build();

        farmService.create(farm);

        Integer id = farmService.findByRegisterNumber("9999999-9").getId();

        farm = Farm.builder()
                            .id(id)
                            .registerNumber("9999999-9")
                            .name("Fazendinha")
                            .info("Olá Mundo")
                            .reference("Ao lado da rua principal")
                            .number(883)
                            .deleted(false)
                            .build();

        farmService.update(farm);

        
        assertThat(farmService.readAll().get(0).getName()).isEqualTo("Fazendinha");
    }


    @DisplayName("Testar a procura de uma Fazenda deletada por Número de Registro.")
	@Test
	@Transactional
	@Rollback
	void testFindByRegisterNumberDeleted() throws Exception {

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(true)
                                .build();

        farmService.create(farm);


        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            farmService.findByRegisterNumber("9999999-9");
        });
    }


    @DisplayName("Testar a criação de um Local com CEP igual a quem já foi deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreatePlaceCepEqualsDeleted() throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(true)
                                .build();

        farmService.create(farm);
        
        farm = Farm.builder()
                            .registerNumber("9999999-9")
                            .name("Fazendinha")
                            .info("Olá Mundo")
                            .reference("Ao lado da rua principal")
                            .number(883)
                            .deleted(false)
                            .build();

        farmService.create(farm);

        assertThat(farmService.readAll().get(0).getName()).isEqualTo("Fazendinha");
    }


    @DisplayName("Testar a procura por Número de Registro existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetRegisterNumbers() throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(false)
                                .build();

        farmService.create(farm);

        assertThat(farmService.getRegisterNumbers("999").get(0)).isEqualTo("9999999-9");
    }

    @DisplayName("Testar a procura por Nomes existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetNames() throws Exception{

        Farm farm = Farm.builder()
                                .registerNumber("9999999-9")
                                .name("Fazenda Teste")
                                .info("Olá Mundo")
                                .reference("Ao lado da rua principal")
                                .number(883)
                                .deleted(false)
                                .build();

        farmService.create(farm);

        assertThat(farmService.getNames("Fazenda").get(0)).isEqualTo("Fazenda Teste");
    }
    
}
