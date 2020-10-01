package br.com.cattle_control.starter.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@Table(name = "PaymentType")
public class PaymentType implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PaymentType_ID")
    private Integer id;

    @Column(name = "PaymentType_Name")
    private String paymentType_name;

    @Column(name = "PaymentType_Deleted")
    private Boolean deleted;


    @Builder 
	public static PaymentType create (
            Integer id,
            String  type_name,
            Boolean deleted) {

        PaymentType instance = new PaymentType();

        instance.setId(id);
        instance.setPaymentType_name(type_name);
        instance.setDeleted(deleted);

        return instance;
    }
}
