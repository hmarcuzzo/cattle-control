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
@Table(name = "TypeExpense")
public class TypeExpense implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TypeExpense_ID")
    private Integer id;

    @Column(name = "TypeExpense_Name")
    private String TypeExpense_name;

    @Column(name = "TypeExpense_Deleted")
    private Boolean deleted;

    @Builder 
	public static TypeExpense create (
            Integer id,
            String  type_name,
            Boolean deleted) {

        TypeExpense instance = new TypeExpense();

        instance.setId(id);
        instance.setTypeExpense_name(type_name);
        instance.setDeleted(deleted);

        return instance;
    }
}
