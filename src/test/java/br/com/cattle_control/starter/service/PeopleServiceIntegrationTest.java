package br.com.cattle_control.starter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleServiceIntegrationTest {
    
    @Autowired
    PeopleService peopleService;

    @DisplayName("Testar a criação de uma Pessoa no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{
        peopleService.create(People.builder()
                                .id(1)
                                .type(1)
                                .idType("41199288888")
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .phone("17991524608")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build());
        // System.out.println(peopleService.readAll().get(0).getName());
        assertThat(peopleService.readAll().get(0).getName()).isEqualTo("Henrique");
    }
}