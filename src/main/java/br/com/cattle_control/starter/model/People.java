package br.com.cattle_control.starter.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.persistence.Entity;
// import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PEOPLE")
public class People implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PEOPLE_ID")
    private int id;

    @Column(name = "PEOPLE_NAME")
    @NotEmpty(message = "*Por favor forneça o nome")
    private String name;

    @Column(name = "EMAIL")
    @Email(message = "*Por favor forneça um e-mail válido")
    private String email;

    @Column(name = "TYPE")
    @NotEmpty(message = "*Por favor defina o tipo")
    private int type;

    @Column(name = "ID_TYPE")
    @Length(min = 11, max= 14, message = "*Por favor forneça todos os dígitos do CPF")
    @NotEmpty(message = "*Por favor forneça um CPF")
    private String idType;


    @Column(name = "PHONE")
    @NotEmpty(message = "*Por favor forneça o telefone")
    @Length(min = 10, max= 25, message = "*Por favor forneça todos os dígitos do telefone")
    private String phone;

    @Column(name = "PEOPLE_INFO")
    @Length(min = 0, max= 1000, message = "*A descrição deve ter no máximo 1000 caracteres")
    private String info;

    // @OneToMany
    // @JoinTable(name = "PEOPLE_ROLE", joinColumns = @JoinColumn(name = "PEOPLE_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    // private Set<Role> roles;

}