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
@Table(name = "CattleExpense")
public class CattleExpense implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CattleExpense_ID")
    private Integer id;

    @Column(name = "CattleExpense_Date")
    private String date;

    @Column(name = "CattleExpense_Info")
    private String info;

    @Column(name = "CattleExpense_Deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name= "id_cattle")
    private Cattle cattle;

    @ManyToOne
    @JoinColumn(name= "id_expense")
    private Expense expense;


    @Builder 
	public static CattleExpense create (
            Integer id,
            String  date,
            String  info,
            Boolean deleted,
            Cattle  cattle,
            Expense expense) {
		
    	
    	CattleExpense instance = new CattleExpense();
    	
    	instance.setId(id);
        instance.setDate(date);
        instance.setInfo(info);
        instance.setDeleted(deleted);
        instance.setCattle(cattle);
        instance.setExpense(expense);
        
        return instance;
    }

}
