package br.com.cattle_control.starter.bean;


import org.omnifaces.util.Faces;


import java.io.IOException;

// import java.util.List;
// import javax.faces.application.FacesMessage;
// import javax.faces.context.FacesContext;

// import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.RequiredArgsConstructor;
	
// import org.primefaces.model.DefaultStreamedContent;
// import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo, Matheus Batistela
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class PeopleFormMB {

    @Autowired
    private final PeopleService peopleService;

    private People people = new People();
    

    // Delegação de Getters and Setters das propriedades de people que serão usadas na tela
    public Integer getId() {
        return people.getId();
    }

    public String getName() {
        return people.getName();
    }

    public String getEmail() {
        return people.getEmail();
    }

    public Integer getType() {
        return people.getType();
    }

    public String getIdType() {
        return people.getIdType(); 
    }

    public String getPhone() {
        return people.getPhone();
    }

    public String getInfo() {
        return people.getInfo();
    }

    public Boolean getDeleted() {
        return people.getDeleted();
    }

    public void setId(Integer id){
        people.setId(id);
    }

    public void setName(String name){
        people.setName(name);
    }

    public void setEmail(String email){
        people.setEmail(email);
    }

    public void setType(Integer type){
        people.setType(type);
    }


    public void setIdType(String idType){
        people.setIdType(idType);
    }

    public void setPhone(String phone){
        people.setPhone(phone);
    }
    
    public void setInfo(String info){
        people.setInfo(info);
    }

    public void setDeleted(Boolean deleted) {
        people.setDeleted(deleted);
    }



    // Ações disponibilizadas na tela
    public void save() {
        this.people = People.builder()
                            .id(getId())
                            .name(getName())
                            .email(getEmail())
                            .type(getType())
                            .idType(getIdType())
                            .phone(getPhone())
                            .info(getInfo())
                            .deleted(false)
        					.build();
               
        String msg;
        try {
            if (getId() == null) {
                peopleService.create(this.people);
                msg = "A pessoa " + getName() + " foi criada com sucesso!";
            } else {
                peopleService.update(this.people);
                msg = "A pessoa " + getName() + " foi atualizada com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Uma pessoa com o CPF/CNPJ " + getIdType() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
        // Faces.getFlash().setKeepMessages(true);
        // Faces.redirect("people-form.xhtml?id=#{peopleFormMB.id}");
        // clear();
    }

    public void clear() {
        people = new People();
    }

    public void remove() throws IOException{
        String msg;
        System.out.println("Valor do ID: " + getId());
        if (has(getId())) {
            this.people = peopleService.readById(getId());
            System.out.println("Valor do Nome: " + getName());
            try {
                setDeleted(true);
                peopleService.update(people);
                
                msg = "A Pessoa " + getName() + " foi removida com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("people-list.jsf");
        }
    }

    

    // Funções auxiliares
    public boolean isNew() {
        return people == null || getId() == null;
    }

    
}
