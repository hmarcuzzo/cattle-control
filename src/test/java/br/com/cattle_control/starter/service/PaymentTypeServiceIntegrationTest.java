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

import br.com.cattle_control.starter.model.PaymentType;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentTypeServiceIntegrationTest {
    @Autowired
    PaymentTypeService paymentTypeService;


    @DisplayName("Testar a criação de um Método de pagamento no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                                .type_name("Parcelado")
                                .deleted(false)
                                .build();

        paymentTypeService.create(paymentType);

        assertThat(paymentTypeService.findByPaymentTypeName("Parcelado").getPaymentType_name()).isEqualTo("Parcelado");
    }

    @DisplayName("Testar a procura de método de pagamento por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByPaymentTypeName() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                                .type_name("Parcelado")
                                .deleted(false)
                                .build();

        paymentTypeService.create(paymentType);

        
        assertThat(paymentTypeService.findByPaymentTypeName("Parcelado")).isEqualTo(paymentType);
    }

    @DisplayName("Testar a procura de método de pagamento por Nome com letras minusculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByPaymentTypeNameWithLowerCase() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                                .type_name("Parcelado")
                                .deleted(false)
                                .build();

        paymentTypeService.create(paymentType);

        
        assertThat(paymentTypeService.findByPaymentTypeName("parcelado")).isEqualTo(paymentType);
    }

    @DisplayName("Testar a procura de método de pagamento por Nome com letras maisculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByPaymentTypeNameWithUpperCase() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                                .type_name("Parcelado")
                                .deleted(false)
                                .build();

        paymentTypeService.create(paymentType);

        
        assertThat(paymentTypeService.findByPaymentTypeName("PARCELADO")).isEqualTo(paymentType);
    }

    @DisplayName("Testar o update de um método de pagamento no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdatePaymentType () throws Exception{

        PaymentType paymentType = PaymentType.builder()
                                .type_name("Parcelado")
                                .deleted(false)
                                .build();

        paymentTypeService.create(paymentType);

        Integer id = paymentTypeService.findByPaymentTypeName("Parcelado").getId();

        paymentType = PaymentType.builder()
                            .id(id)
                            .type_name("Cheque")
                            .deleted(false)
                            .build();

        paymentTypeService.update(paymentType);

        
        assertThat(paymentTypeService.findByPaymentTypeName("Cheque")).isEqualTo(paymentType);
    }

    @DisplayName("Testar a procura de um método de pagamento deletado por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByPaymentTypeNameDeleted() throws Exception {

        PaymentType paymentType = PaymentType.builder()
                            .type_name("Cheque")
                            .deleted(true)
                            .build();

        paymentTypeService.create(paymentType);


        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            paymentTypeService.findByPaymentTypeName("Cheque");
        });
    }

    @DisplayName("Testar a criação de um método de pagamento com Nome igual a quem já foi deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreatePlaceCepEqualsDeleted() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                            .type_name("Cheque")
                            .deleted(true)
                            .build();

        paymentTypeService.create(paymentType);
        
        paymentType = PaymentType.builder()
                    .type_name("Cheque")
                    .deleted(false)
                    .build();

        paymentTypeService.create(paymentType);

        assertThat(paymentTypeService.findByPaymentTypeName("Cheque")).isEqualTo(paymentType);
    }


    @DisplayName("Testar a procura por nome do método de pagamento existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetPaymentTypeNames() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                            .type_name("Cheque")
                            .deleted(false)
                            .build();

        paymentTypeService.create(paymentType);

        assertThat(paymentTypeService.getPaymentTypeNames("Che").get(0)).isEqualTo("Cheque");
    }

    @DisplayName("Testar a procura por nome do método de pagamento existentes em UpperCase.")
	@Test
	@Transactional
	@Rollback
	void testGetPaymentTypeNamesWithUpperCase() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                            .type_name("Cheque")
                            .deleted(false)
                            .build();

        paymentTypeService.create(paymentType);

        assertThat(paymentTypeService.getPaymentTypeNames("CHE").get(0)).isEqualTo("Cheque");
    }

    @DisplayName("Testar a procura por nome do método de pagamento existentes em LowerCase.")
	@Test
	@Transactional
	@Rollback
	void testGetPaymentTypeNamesWithLowerCase() throws Exception{

        PaymentType paymentType = PaymentType.builder()
                            .type_name("Cheque")
                            .deleted(false)
                            .build();

        paymentTypeService.create(paymentType);

        assertThat(paymentTypeService.getPaymentTypeNames("che").get(0)).isEqualTo("Cheque");
    }

}
