package br.com.cattle_control.starter.view;

import org.omnifaces.util.Faces;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PlaceService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;


@Component
@RequestScope
@RequiredArgsConstructor
public class PlaceFormView {
    

    @Autowired
    private final PlaceService placeService;

    private Place place = new Place();

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String personId = paramMap.get("id");

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (personId != null) {

            int id = Integer.parseInt(personId); 
            place = placeService.readById(id);
        } 
    }


    // Delegação de Getters and Setters das propriedades de place que serão usadas na tela
    public Integer getId() {
        return place.getId();
    }

    public String getCep() {
        return place.getCep();
    }

    public String getCity() {
        return place.getCity();
    }

    public Boolean getDeleted() {
        return place.getDeleted();
    }

    public void setId(Integer id){
        place.setId(id);
    }

    public void setCep(String cep) {
        place.setCep(cep);
    }

    public void setCity(String city) {
        place.setCity(city);
    }

    public void setDeleted(Boolean deleted) {
        place.setDeleted(deleted);
    }



    // Ações disponibilizadas na tela
    public void save() {
        this.place = Place.builder()
                            .id(getId())
                            .cep(getCep())
                            .city(getCity())
                            .deleted(false)
        					.build();
               
        String msg;
        try {
            if (getId() == null) {
                placeService.create(this.place);
                msg = "O local " + getCity() + " foi criado com sucesso!";
            } else {
                placeService.update(this.place);
                msg = "O local " + getCity() + " foi atualizado com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Um local com o CEP " + getCep() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        place = new Place();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.place = placeService.readById(getId());
            try {
                setDeleted(true);
                placeService.update(place);
                
                msg = "O local " + getCity() + " foi removido com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("place-list.jsf");
        } else {
            throw new BusinessException("Não existe nenhum local para ser deletado!");
        }
    }



    // Funções auxiliares
    public boolean isNew() {
        return place == null || getId() == null;
    }
}
