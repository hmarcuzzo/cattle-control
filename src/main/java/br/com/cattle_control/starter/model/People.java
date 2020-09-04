package br.com.cattle_control.starter.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@Table(name = "People")
public class People implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "People_ID")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer id;

    @Column(name = "People_Type")
    @NotNull(message = "*Por favor defina o tipo")
    private Integer type;

    @Column(name = "People_ID_Type")
    @Length(min = 11, max= 14, message = "*Por favor forneça todos os dígitos do CPF")
    @NotEmpty(message = "*Por favor forneça um CPF")
    private String idType;

    @Column(name = "People_Name")
    @NotEmpty(message = "*Por favor forneça o nome")
    private String name;

    @Column(name = "People_Phone")
    @NotEmpty(message = "*Por favor forneça o telefone")
    @Length(min = 10, max= 25, message = "*Por favor forneça todos os dígitos do telefone")
    private String phone;

    @Column(name = "People_Email")
    @Email(message = "*Por favor forneça um e-mail válido")
    private String email;

    @Column(name = "People_Info")
    @Length(min = 0, max= 1000, message = "*A descrição deve ter no máximo 1000 caracteres")
    private String info;

    @Column(name = "People_Deleted")
    private Boolean deleted;

    @Builder 
	public static People create (
            Integer id,
			Integer type,
			String  idType,
            String  name,
			String  phone,
			String  email,
			String  info,
			Boolean deleted) {
		
    	
    	People instance = new People();
    	
    	instance.setId(id);
        instance.setType(type);
        instance.setIdType(idType);
    	instance.setName(name);
    	instance.setPhone(phone);
    	instance.setEmail(email);
    	instance.setInfo(info);
        instance.setDeleted(deleted);
        
        return instance;
    }

    // @OneToMany
    // @JoinTable(name = "People_Role", joinColumns = @JoinColumn(name = "People_ID"), inverseJoinColumns = @JoinColumn(name = "Role_ID"))
    // private Set<Role> roles;

}