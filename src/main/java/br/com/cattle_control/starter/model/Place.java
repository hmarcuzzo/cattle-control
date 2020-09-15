package br.com.cattle_control.starter.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// import org.hibernate.annotations.OnDelete;
// import org.hibernate.annotations.OnDeleteAction;
// import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
// import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.NotNull;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Place")
public class Place implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Place_ID")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer id;

    @Column(name = "Place_Cep")
    // @NotNull(message = "*Por favor defina o tipo")
    private String cep;

    @Column(name = "Place_City")
    // @NotEmpty(message = "*Por favor forneça o nome")
    private String city;

    @Column(name = "Place_Deleted")
    private Boolean deleted;


    @Builder 
	public static Place create (
            Integer id,
            String cep,
            String city,
            Boolean deleted) {

        Place instance = new Place();

        instance.setId(id);
        instance.setCep(cep);
        instance.setCity(city);
        instance.setDeleted(deleted);

        return instance;
    }
    
}
