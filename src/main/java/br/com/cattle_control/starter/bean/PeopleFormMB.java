package br.com.cattle_control.starter.bean;


import javax.faces.view.ViewScoped;
import org.omnifaces.util.Faces;


import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import lombok.RequiredArgsConstructor;
	
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;


@Component
@RequestScope
@RequiredArgsConstructor
public class PeopleFormMB implements Serializable {
    
    private People people = new People();
    
    @Autowired
    private final PeopleService peopleService;


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



    // Ações disponibilizadas na tela
    public void save() {
        this.people = People.builder()
                            .name(people.getName())
                            .email(people.getEmail())
                            .type(1)
                            .idType(people.getIdType())
                            .phone(people.getPhone())
                            .info(people.getInfo())
                            .deleted(false)
        					.build();
        
        FacesContext.getCurrentInstance()
			.getExternalContext()
			.getFlash()
            .setKeepMessages(true);
            
        String msg;
        
        try {
            if (people.getId() == null) {
                peopleService.create(this.people);
                msg = "A pessoa " + people.getName() + " foi criada com sucesso!";
            } else {
                peopleService.update(this.people);
                msg = "A pessoa " + people.getName() + " foi atualizada com sucesso!";
            }

            // FacesContext.getCurrentInstance()
            //             .addDetailMessage("ola")
            // 			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
            // 												"Info", 
            // 												String.format("Pessoa [%s] criada com sucesso!", 
            // 												this.people.getName())));  
            
            // return "index.xhtml?faces-redirect=true";
        } catch (final EntityAlreadyExistsException e) {
            // FacesContext.getCurrentInstance()
            // 			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            // 												"Erro", 
            // 												"Já existe um arquivo com esse nome!"));
            // return "index.xhtml?indice=1";
            msg = "A pessoa " + people.getName() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            // FacesContext.getCurrentInstance()
            // 			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            // 												"Erro", 
            // 												"Erro na gravação dos dados!"));
            // return "index.xhtml";

            msg = "Erro na gravação dos dados!";
        }

        addDetailMessage(msg);
        clear();
    }

    public void clear() {
        people = new People();
    }

    

    // Funções auxiliares
    public boolean isNew() {
        return people == null || people.getId() == null;
    }
}
