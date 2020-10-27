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

import br.com.cattle_control.starter.model.TypeExpense;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TypeExpenseServiceIntegrationTest {
    @Autowired
    TypeExpenseService typeExpenseService;


    @DisplayName("Testar a criação de um tipo de gasto no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);

        assertThat(typeExpenseService.findByTypeExpenseName("Teste")).isEqualTo(typeExpense);
    }

    @DisplayName("Testar a procura de um tipo de gasto por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByTypeExpenseName() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);
        
        assertThat(typeExpenseService.findByTypeExpenseName("Teste").getTypeExpense_name()).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura de um tipo de gasto por Nome com letras minusculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByTypeExpenseNameWithLowerCase() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);
   
        assertThat(typeExpenseService.findByTypeExpenseName("teste").getTypeExpense_name()).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura de um tipo de gasto por Nome com letras maisculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByTypeExpenseNameWithUpperCase() throws Exception{
        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);
       
        assertThat(typeExpenseService.findByTypeExpenseName("TESTE").getTypeExpense_name()).isEqualTo("Teste");
    }

    @DisplayName("Testar o update de um tipo de gasto no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateTypeExpense () throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);
       
        Integer id = typeExpenseService.findByTypeExpenseName("Teste").getId();

        typeExpense = TypeExpense.builder()
                            .id(id)
                            .type_name("Teste2")
                            .deleted(false)
                            .build();

        typeExpenseService.update(typeExpense);

        
        assertThat(typeExpenseService.findByTypeExpenseName("Teste2")).isEqualTo(typeExpense);
    }

    @DisplayName("Testar a procura de um tipo de gasto deletado por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByTypeExpenseNameDeleted() throws Exception {

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(true)
                            .build();

        typeExpenseService.create(typeExpense);

        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            typeExpenseService.findByTypeExpenseName("Teste");
        });
    }

    @DisplayName("Testar a criação de um tipo de gasto com Nome igual a quem já foi deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreateTypeExpenseNameEqualsDeleted() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(true)
                            .build();

        typeExpenseService.create(typeExpense);
        
        typeExpense = TypeExpense.builder()
                    .type_name("Teste")
                    .deleted(false)
                    .build();

        typeExpenseService.create(typeExpense);

        assertThat(typeExpenseService.findByTypeExpenseName("Teste")).isEqualTo(typeExpense);
    }


    @DisplayName("Testar a procura por nome do tipo de gasto existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetTypeExpenseNames() throws Exception{
        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);

        assertThat(typeExpenseService.getTypeExpenseNames("Tes").get(0)).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura por nome do tipo de gasto existentes em UpperCase.")
	@Test
	@Transactional
	@Rollback
	void testGetTypeExpenseNamesWithUpperCase() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);

        assertThat(typeExpenseService.getTypeExpenseNames("TES").get(0)).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura por nome do tipo de gastos existentes em LowerCase.")
	@Test
	@Transactional
	@Rollback
	void testGetTypeExpenseNamesWithLowerCase() throws Exception{

        TypeExpense typeExpense = TypeExpense.builder()
                            .type_name("Teste")
                            .deleted(false)
                            .build();

        typeExpenseService.create(typeExpense);

        assertThat(typeExpenseService.getTypeExpenseNames("tes").get(0)).isEqualTo("Teste");
    }


    @DisplayName("Testar a lista de todos os tipos de gastos.")
	@Test
	@Transactional
	@Rollback
	void testReadAllTypeExpenseName() throws Exception{

        assertThat(typeExpenseService.readAllTypeExpenseName().get(0)).isEqualTo("Vacina");
    }
    
}
