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

import br.com.cattle_control.starter.model.Action;
import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.model.PaymentType;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ActionServiceInegrationTest {
    @Autowired
    ActionService actionService;

    @Autowired
    PeopleService peopleService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PaymentTypeService paymentTypeService;


    @Transactional
    @Rollback
    People createPeopleSeller() throws Exception{
        People people = People.builder()
                                .name("Vendedor")
                                .email("vendedor@hotmail.com")
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
    People createPeopleBuyer() throws Exception{
        People people = People.builder()
                                .name("Comprador")
                                .email("comprador@hotmail.com")
                                .type(1)
                                .idType("999.999.999-99")
                                .phone("18992686498")
                                .info("Olá Mundo!")
                                .deleted(false)
                                .build();

        return peopleService.create(people);
    }

    @Transactional
    @Rollback
    People createPeopleDeliveryman() throws Exception{
        People people = People.builder()
                                .name("Entregador")
                                .email("entregador@hotmail.com")
                                .type(1)
                                .idType("777.777.777-77")
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

    @DisplayName("Testar a criação de uma ação no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        People people_buyer = createPeopleBuyer();
        People people_seller = createPeopleSeller();
        People people_deliveryman = createPeopleDeliveryman();
        Place place = createPlace();
        PaymentType payment_type = paymentTypeService.findByPaymentTypeName("Cheque");

        Action action = Action.builder()
                                .people_buyer(people_buyer)
                                .people_seller(people_seller)
                                .people_deliveryman(people_deliveryman)
                                .amount(5)
                                .value(250.00)
                                .date("02/10/2020")
                                .payment_type(payment_type)
                                .divided(0)
                                .deadline("10/10/2020")
                                .place(place)
                                .district("Centro")
                                .reference("Ao lado do sistema")
                                .number(885)
                                .info("Uma informação qualquer")
                                .deleted(false)
                                .build();

        actionService.create(action);

        assertThat(actionService.readById(action.getId())).isEqualTo(action);
    }

    @DisplayName("Testar o update de uma ação no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateAction () throws Exception{
        People people_buyer = createPeopleBuyer();
        People people_seller = createPeopleSeller();
        People people_deliveryman = createPeopleDeliveryman();
        Place place = createPlace();
        PaymentType payment_type = paymentTypeService.findByPaymentTypeName("Cheque");

        Action action = Action.builder()
                                .people_buyer(people_buyer)
                                .people_seller(people_seller)
                                .people_deliveryman(people_deliveryman)
                                .amount(5)
                                .value(250.00)
                                .date("02/10/2020")
                                .payment_type(payment_type)
                                .divided(0)
                                .deadline("10/10/2020")
                                .place(place)
                                .district("Centro")
                                .reference("Ao lado do sistema")
                                .number(885)
                                .info("Uma informação qualquer")
                                .deleted(false)
                                .build();

        actionService.create(action);

        Integer id = action.getId();

        action = Action.builder()
                                .id(id)
                                .people_buyer(people_buyer)
                                .people_seller(people_seller)
                                .people_deliveryman(people_seller)
                                .amount(5)
                                .value(250.00)
                                .date("02/10/2020")
                                .payment_type(payment_type)
                                .divided(0)
                                .deadline("10/10/2020")
                                .place(place)
                                .district("Centro")
                                .reference("Ao lado do sistema")
                                .number(885)
                                .info("Uma informação qualquer")
                                .deleted(false)
                                .build();

        actionService.update(action);

        
        assertThat(actionService.readById(id).getPeople_deliveryman()).isEqualTo(people_seller);
    }

    @DisplayName("Testar a procura de uma venda deletada.")
	@Test
	@Transactional
	@Rollback
	void testFindActionDeleted() throws Exception {
        People people_buyer = createPeopleBuyer();
        People people_seller = createPeopleSeller();
        People people_deliveryman = createPeopleDeliveryman();
        Place place = createPlace();
        PaymentType payment_type = paymentTypeService.findByPaymentTypeName("Cheque");

        Action action = Action.builder()
                                .people_buyer(people_buyer)
                                .people_seller(people_seller)
                                .people_deliveryman(people_deliveryman)
                                .amount(5)
                                .value(250.00)
                                .date("02/10/2020")
                                .payment_type(payment_type)
                                .divided(0)
                                .deadline("10/10/2020")
                                .place(place)
                                .district("Centro")
                                .reference("Ao lado do sistema")
                                .number(885)
                                .info("Uma informação qualquer")
                                .deleted(true)
                                .build();

        actionService.create(action);


        assertThrows(javax.persistence.EntityNotFoundException.class, () -> {
            actionService.readById(action.getId());
        });
    }
}
