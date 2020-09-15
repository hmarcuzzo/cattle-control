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
@Table(name = "Farm")
public class Farm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Farm_ID")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer id;

    @Column(name = "Farm_register_name")
    private String registerNumber;

    @Column(name = "Farm_name")
    private String name;

    @Column(name = "Farm_info")
    private String info;

    @Column(name = "Farm_reference")
    private String reference;

    @Column(name = "Farm_number")
    private Integer number;

    @Column(name = "Farm_Deleted")
    private Boolean deleted;

    @Builder 
	public static Farm create (
            Integer id,
			String  registerNumber,
            String  name,
			String  info,
			String  reference,
			Integer number,
			Boolean deleted) {
		
    	
    	Farm instance = new Farm();
    	
    	instance.setId(id);
        instance.setRegisterNumber(registerNumber);
        instance.setName(name);
    	instance.setInfo(info);
    	instance.setReference(reference);
    	instance.setNumber(number);
        instance.setDeleted(deleted);
        
        return instance;
    }

}