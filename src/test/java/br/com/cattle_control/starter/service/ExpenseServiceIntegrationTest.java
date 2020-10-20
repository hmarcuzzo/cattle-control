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

import br.com.cattle_control.starter.model.Expense;
import br.com.cattle_control.starter.model.TypeExpense;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseServiceIntegrationTest {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    TypeExpenseService typeExpenseService;

    @Transactional
    @Rollback
    TypeExpense createTypeExpense() throws Exception{
        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        return typeExpenseService.create(typeExpense);
    }


    @DisplayName("Testar a criação de um gasto no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        TypeExpense typeExpense = createTypeExpense();

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(false)
                                .typeExpense(typeExpense)
                                .build();

        expenseService.create(expense);

        assertThat(expenseService.readById(expense.getId()).getExpense_name()).isEqualTo("Vacina do COVID-19");
    }


    @DisplayName("Testar a procura de um gasto por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByName() throws Exception{
        TypeExpense typeExpense = createTypeExpense();

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(false)
                                .typeExpense(typeExpense)
                                .build();

        expenseService.create(expense);

        
        assertThat(expenseService.findByName("Vacina do COVID-19").get(0)).isEqualTo(expense);
    }

    @DisplayName("Testar o update de uma gasto no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateExpense () throws Exception{
        TypeExpense typeExpense = createTypeExpense();

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(false)
                                .typeExpense(typeExpense)
                                .build();

        expenseService.create(expense);

        Integer id = expense.getId();

        expense = Expense.builder()
                            .id(id)
                            .expense_name("Vacina do COVID-20")
                            .expense_priceUnit(3.50)
                            .expense_yield(1)
                            .deleted(false)
                            .typeExpense(typeExpense)
                            .build();

        expenseService.update(expense);

        
        assertThat(expenseService.readById(id).getExpense_name()).isEqualTo("Vacina do COVID-20");
    }


    @DisplayName("Testar a procura de uma Fazenda deletada por ID.")
	@Test
	@Transactional
	@Rollback
	void testReadByIdDeleted() throws Exception {
        TypeExpense typeExpense = createTypeExpense();

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(true)
                                .typeExpense(typeExpense)
                                .build();

        expenseService.create(expense);

        Integer id = expense.getId();


        assertThrows(javax.persistence.EntityNotFoundException.class, () -> {
            expenseService.readById(id);
        });
    }

    @DisplayName("Testar a procura de um gasto por Nomes existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetNames() throws Exception{
        TypeExpense typeExpense = createTypeExpense();

        Expense expense = Expense.builder()
                                .expense_name("Vacina do COVID-19")
                                .expense_priceUnit(3.50)
                                .expense_yield(1)
                                .deleted(false)
                                .typeExpense(typeExpense)
                                .build();

        expenseService.create(expense);

        assertThat(expenseService.getIds(Integer.toString(expense.getId())).get(0)).isEqualTo(expense.getId());
    }
}
