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

import br.com.cattle_control.starter.model.*;




@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CattleExpenseServiceIntegrationTest {

    @Autowired
    CattleExpenseService cattleExpenseService;

    @Autowired
    CattleService cattleService;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    FarmService farmService;

    @Autowired
    TypeExpenseService typeExpenseService;

    @Transactional
    @Rollback
    Cattle createCattle() throws Exception {
        Farm farm = farmService.readAll().get(0);

        Cattle cattle = Cattle.builder()
                                .numbering("911")
                                .weight(450.5)
                                .price(2000)
                                .info("Olá Mundo!")
                                .deleted(false)
                                .farm(farm)
                                .build();

        return cattleService.create(cattle);
    }

    @Transactional
    @Rollback
    Expense createExpense() throws Exception {
        TypeExpense typeExpense = typeExpenseService.readAll().get(0);

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(false)
                                .typeExpense(typeExpense)
                                .build();
        return expenseService.create(expense);
    }


    @DisplayName("Testar a criação de um gasto de boi no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreateCattleExpense() throws Exception {

        Cattle cattle = createCattle();
        Expense expense = createExpense();

        CattleExpense cattleExpense = CattleExpense.builder()
                                        .date("15/10/2020")
                                        .info("Olá Mundo")
                                        .cattle(cattle)
                                        .expense(expense)
                                        .deleted(false)
                                        .build();

        cattleExpenseService.create(cattleExpense);

        assertThat(cattleExpenseService.readById(cattleExpense.getId())).isEqualTo(cattleExpense);
    }


    @DisplayName("Testar a procura de um gasto de boi pela número de registro.")
	@Test
	@Transactional
	@Rollback
	void testFindById() throws Exception {
        Cattle cattle = createCattle();
        Expense expense = createExpense();

        CattleExpense cattleExpense = CattleExpense.builder()
                                        .date("15/10/2020")
                                        .info("Olá Mundo")
                                        .cattle(cattle)
                                        .expense(expense)
                                        .deleted(false)
                                        .build();

        cattleExpenseService.create(cattleExpense);

        
        assertThat(cattleExpenseService.readById(cattleExpense.getId())).isEqualTo(cattleExpense);
    }

    @DisplayName("Testar o update de um gasto de boi no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateCattleExpense () throws Exception {
        Cattle cattle = createCattle();
        Expense expense = createExpense();

        CattleExpense cattleExpense = CattleExpense.builder()
                                        .date("15/10/2020")
                                        .info("Olá Mundo")
                                        .cattle(cattle)
                                        .expense(expense)
                                        .deleted(false)
                                        .build();

        cattleExpenseService.create(cattleExpense);

        Integer id = cattleExpense.getId();

        cattleExpense = CattleExpense.builder()
                                        .id(id)
                                        .date("15/10/2020")
                                        .info("Olá Mundo!!")
                                        .cattle(cattle)
                                        .expense(expense)
                                        .deleted(false)
                                        .build();

        cattleExpenseService.update(cattleExpense);

        
        assertThat(cattleExpenseService.readById(cattleExpense.getId()).getInfo()).isEqualTo("Olá Mundo!!");
    }


    @DisplayName("Testar a procura de um gasto de boi deletado por id.")
	@Test
	@Transactional
	@Rollback
	void testFindByIdDeleted() throws Exception {
        Cattle cattle = createCattle();
        Expense expense = createExpense();

        CattleExpense cattleExpense = CattleExpense.builder()
                                        .date("15/10/2020")
                                        .info("Olá Mundo")
                                        .cattle(cattle)
                                        .expense(expense)
                                        .deleted(true)
                                        .build();

        cattleExpenseService.create(cattleExpense);


        assertThrows(javax.persistence.EntityNotFoundException.class, () -> {
            cattleExpenseService.readById(cattleExpense.getId());
        });
    }
  
}
