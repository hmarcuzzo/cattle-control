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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
// @SpringBootTest(classes = {PeopleService.class})
// @SpringJUnitConfig(PeopleService.class)
public class PeopleServiceIntegrationTest {
    
    @Autowired
    PeopleService peopleService;

    @DisplayName("Testar a criação de uma Pessoa no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("41199288888")
                                .phone("17991524608")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();
        // assertThat(people.getId()).isEqualTo(1);
        peopleService.create(people);

        // people = People.builder()
        //                     .name("Henrique")
        //                     .email("henrique@hotmail.com")
        //                     .type(1)
        //                     .idType("41199288888")
        //                     .phone("17991524608")
        //                     .info("Olá Mundo!")
        //                     .deleted(false)
        //                     .build();

        // people.setName("João");
        people.setDeleted(true);


        // peopleService.create(people);
        peopleService.update(people);
        // peopleService.delete(people.getId());

        assertThat(peopleService.readAll().get(0).getDeleted()).isEqualTo(true);
    }
}