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
@Table(name = "Expense")
public class Expense implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Expense_ID")
    private Integer id;

    @Column(name = "Expense_Name")
    private String expense_name;

    @Column(name = "Expense_PriceUnit")
    private Double expense_priceUnit;

    @Column(name = "Expense_Yield")
    private Integer expense_yield;

    @Column(name = "Expense_Deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name= "id_typeExpense")
    private TypeExpense typeExpense;

    @Builder 
	public static Expense create (
            Integer id,
            String  expense_name,
            Double  expense_priceUnit,
            Integer expense_yield,
            TypeExpense typeExpense,
            Boolean deleted) {

        Expense instance = new Expense();

        instance.setId(id);
        instance.setExpense_name(expense_name);
        instance.setExpense_priceUnit(expense_priceUnit);
        instance.setExpense_yield(expense_yield);
        instance.setTypeExpense(typeExpense);
        instance.setDeleted(deleted);

        return instance;
    }

}
