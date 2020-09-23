package br.com.cattle_control.starter.view;

import org.omnifaces.util.Faces;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import javax.faces.context.FacesContext;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.FarmService;
import br.com.cattle_control.starter.service.PeopleService;
import br.com.cattle_control.starter.service.PlaceService;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo, Matheus Batistela
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class FarmFormView {

    @Autowired
    private final FarmService farmService;

    @Autowired
    private final PeopleService peopleService;

    @Autowired
    private final PlaceService placeService;

    private Farm farm = new Farm();
    private String idPeople;
    private String idPlace;

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String farmId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (farmId != null) {

            int id = Integer.parseInt(farmId); 
            farm = farmService.readById(id);

            this.idPeople = farm.getPeople().getIdType();
            this.idPlace = farm.getPlace().getCep();
        } 

    }
    

    // Delegação de Getters and Setters das propriedades de farm que serão usadas na tela
    public Integer getId() {
        return farm.getId();
    }

    public String getName() {
        return farm.getName();
    }

    public String getInfo() {
        return farm.getInfo();
    }

    public Integer getNumber() {
        return farm.getNumber();
    }

    public String getReference() {
        return farm.getReference(); 
    }

    public String getRegisterNumber() {
        return farm.getRegisterNumber();
    }

    public Boolean getDeleted() {
        return farm.getDeleted();
    }

    public String getIdPeople() {
        return this.idPeople;
    }

    public String getIdPlace() {
        return this.idPlace;
    }

    public void setId(Integer id){
        farm.setId(id);
    }

    public void setName(String name){
        farm.setName(name);
    }

    public void setRegisterNumber(String registerNumber){
        farm.setRegisterNumber(registerNumber);
    }

    public void setNumber(Integer number){
        farm.setNumber(number);
    }

    public void setReference(String reference){
        farm.setReference(reference);
    }

    public void setInfo(String info){
        farm.setInfo(info);
    }

    public void setDeleted(Boolean deleted) {
        farm.setDeleted(deleted);
    }

    public void setIdPeople(String idPeople) {
        this.idPeople = idPeople;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }



    // Ações disponibilizadas na tela
    public void save() {
        People people = peopleService.findByIdType(this.idPeople);
        Place place = placeService.findByCep(this.idPlace);

        this.farm = Farm.builder()
                            .id(getId())
                            .name(getName())
                            .reference(getReference())
                            .registerNumber(getRegisterNumber())
                            .number(getNumber())
                            .info(getInfo())
                            .deleted(false)
                            .people(people)
                            .place(place)
        					.build();
               
        String msg;
        try {
            if (getId() == null) {
                farmService.create(this.farm);
                msg = "A fazenda " + getName() + " foi criada com sucesso!";
            } else {
                farmService.update(this.farm);
                msg = "A fazenda " + getName() + " foi atualizada com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Uma fazenda com o número de resitro " + getRegisterNumber() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        farm = new Farm();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.farm = farmService.readById(getId());
            try {
                setDeleted(true);
                farmService.update(farm);
                
                msg = "A fazenda " + getName() + " foi removida com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("farm-list.jsf");
        } else {
            throw new BusinessException("Não existe fazenda para ser deletada!");
        }
    }

    

    // Funções auxiliares
    public boolean isNew() {
        return farm == null || getId() == null;
    }

    public List<String> completeIdType(String query) {
        List<String> result = peopleService.getIDsType(query);
        return result;
    }

    public List<String> completeCep(String query) {
        List<String> result = placeService.getCeps(query);
        return result;
    }

    
}
