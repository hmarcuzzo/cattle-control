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
@Table(name = "Role")
public class Role implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Role_ID")
    private Integer id;

    @Column(name = "Role_Name")
    private String name;

    @Column(name = "Role_Deleted")
    private Boolean deleted;


    @Builder 
	public static Role create (
            Integer id,
            String  name,
            Boolean deleted) {

        Role instance = new Role();

        instance.setId(id);
        instance.setName(name);
        instance.setDeleted(deleted);

        return instance;
    }
}
