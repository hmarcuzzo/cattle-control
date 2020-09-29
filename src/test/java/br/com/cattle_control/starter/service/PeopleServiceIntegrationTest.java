package br.com.cattle_control.starter.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mockito;
// import org.mockito.Spy;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.People;
// import br.com.cattle_control.starter.exception.AnyPersistenceException;
// import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
// import br.com.cattle_control.starter.service.PeopleService;


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
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        peopleService.create(people);


        assertThat(peopleService.findByIdType("41199288888").getName()).isEqualTo("Henrique");
    }

    @DisplayName("Testar o update de uma Pessoa no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdate() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("41199288888")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        peopleService.create(people);
        
        Integer id = peopleService.findByIdType("41199288888").getId();
        
        people = People.builder()
                            .id(id)
                            .name("Henrique Souza")
                            .email("henrique@hotmail.com")
                            .type(1)
                            .idType("41199288888")
                            .phone("18992686498")
                            .info("Olá Mundo!")
                            .deleted(false)
                            .build();

        peopleService.update(people);


        assertThat(peopleService.findByIdType("41199288888").getName()).isEqualTo("Henrique Souza");
    }


    @DisplayName("Testar a procura de uma Pessoa por IdType.")
	@Test
	@Transactional
	@Rollback
	void testFindByIdType() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        peopleService.create(people);

        
        assertThat(peopleService.findByIdType("888.888.888-88")).isEqualTo(people);
    }

    @DisplayName("Testar a procura de uma Pessoa deletada por IdType.")
	@Test
	@Transactional
	@Rollback
	void testFindByIdTypeDeleted() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(true)
                                .build();

        peopleService.create(people);


        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            peopleService.findByIdType("888.888.888-88");
        });
    }


    @DisplayName("Testar a criação de uma Pessoa com CPF igual a quem já foi deletada.")
	@Test
	@Transactional
	@Rollback
	void testCreatePeopleIdTypeEqualsDeleted() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(true)
                                .build();

        peopleService.create(people);

        
        people = People.builder()
                            .name("Henrique Souza")
                            .email("henrique@hotmail.com")
                            .type(1)
                            .idType("888.888.888-88")
                            .phone("18992686498")
                            .info("Olá Mundo!")
                            .deleted(false)
                            .build();

        peopleService.create(people);

        assertThat(peopleService.findByIdType("888.888.888-88").getDeleted()).isEqualTo(false);
    }


    @DisplayName("Testar a procura por IDsType existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetIDsType() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        peopleService.create(people);

        assertThat(peopleService.getIDsType("888").get(0)).isEqualTo("888.888.888-88");
    }

    @DisplayName("Testar a procura por Nomes existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetNames() throws Exception{

        People people = People.builder()
                                .name("Henrique")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        peopleService.create(people);

        assertThat(peopleService.getNames("henri").get(0)).isEqualTo("Henrique");
    }

}