package br.com.cattle_control.starter.service;


import static org.junit.jupiter.api.Assertions.assertThrows;

// import com.jayway.jsonpath.internal.function.text.Length;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.*;




@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CattleServiceIntegrationTest {

    @Autowired
    CattleService cattleService;

    @Autowired
    FarmService farmService;

    @Autowired
    PeopleService peopleService;

    @Autowired
    PlaceService placeService;

    
	@Transactional
    @Rollback
    People createPeople() throws Exception{
        People people = People.builder()
                                .name("Henrique Souza")
                                .email("henrique@hotmail.com")
                                .type(1)
                                .idType("888.888.888-88")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        return peopleService.create(people);
    }

    @Transactional
    @Rollback
    Place createPlace() throws Exception{
        Place place = Place.builder()
                                .cep("55555-000")
                                .city("Testolândia")
                                .deleted(false)
                                .build();

        return placeService.create(place);
    }

    
	@Transactional
    @Rollback
    Farm createFarm() throws Exception{

        Place place = createPlace();
        People people = createPeople();

        Farm farm = Farm.builder()
                            .registerNumber("9999999-9")
                            .name("Fazenda Teste")
                            .info("Olá Mundo")
                            .reference("Ao lado da rua principal")
                            .number(883)
                            .deleted(false)
                            .people(people)
                            .place(place)
                            .build();

        return farmService.create(farm);
    }


    @DisplayName("Testar a criação de um boi no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreateCattle() throws Exception{

        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Olá Mundo")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);

        assertThat(cattleService.findByNumbering("123456").getInfo()).isEqualTo("Olá Mundo");
    }


    @DisplayName("Testar a procura de um Boi pela numeração.")
	@Test
	@Transactional
	@Rollback
	void testFindByNumbering() throws Exception{
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Olá Mundo")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);

        
        assertThat(cattleService.findByNumbering("123456")).isEqualTo(cattle);
    }

    @DisplayName("Testar o update de um noi no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateCattle () throws Exception{
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Olá Mundo")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);

        Integer id = cattleService.findByNumbering("123456").getId();

        cattle = Cattle.builder()
                            .id(id)
                            .numbering("123456")
                            .info("Outra info")
                            .weight(350.8)
                            .price(128.50)
                            .deleted(false)
                            .farm(farm)
                            .build();
        cattleService.update(cattle);

        
        assertThat(cattleService.findByNumbering("123456").getInfo()).isEqualTo("Outra info");
    }


    @DisplayName("Testar a procura de um boi deletado por numeração.")
	@Test
	@Transactional
	@Rollback
	void testFindByNumberingDeleted() throws Exception {
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Outra info")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(true)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);


        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            cattleService.findByNumbering("123456");
        });
    }


    @DisplayName("Testar a criação de um boi com numeração igual a um que já foi deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreateCattleEqualsDeleted() throws Exception{
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Outra info")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(true)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);
        
        cattle = Cattle.builder()
                        .numbering("123456")
                        .info("Outra info")
                        .weight(350.8)
                        .price(128.50)
                        .deleted(false)
                        .farm(farm)
                        .build();

        cattleService.create(cattle);

        assertThat(cattleService.findByNumbering("123456").getInfo()).isEqualTo("Outra info");
    }


    @DisplayName("Testar a procura por numerações existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetNumberings() throws Exception{
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Outra info")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);

        assertThat(cattleService.getNumberings("123").get(0)).isEqualTo("123456");
    }


    @DisplayName("Testar listar todos os bois de uma fazenda.")
	@Test
	@Transactional
	@Rollback
	void testFindAllCattleFromFarm() throws Exception{
        Farm farm = createFarm();

        Cattle cattle = Cattle.builder()
                                .numbering("123456")
                                .info("Outra info")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);

        cattle = Cattle.builder()
                                .numbering("12345678")
                                .info("Outra info")
                                .weight(350.8)
                                .price(128.50)
                                .deleted(false)
                                .farm(farm)
                                .build();

        cattleService.create(cattle);
        
        assertThat(cattleService.findAllCattleFromFarm(farm.getRegisterNumber()).size()).isEqualTo(2);
    }

}
