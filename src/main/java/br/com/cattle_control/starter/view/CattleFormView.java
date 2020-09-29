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

import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.CattleService;
import br.com.cattle_control.starter.service.FarmService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo, Matheus Batistela
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class CattleFormView {

    @Autowired
    private final CattleService cattleService;

    @Autowired
    private final FarmService farmService;

    private Cattle cattle = new Cattle();
    private String farmRegisterNumber;

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String cattleId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (cattleId != null) {

            int id = Integer.parseInt(cattleId); 
            cattle = cattleService.readById(id);

            this.farmRegisterNumber = cattle.getFarm().getRegisterNumber();
        
        } 

    }
    

    // Delegação de Getters and Setters das propriedades de cattle que serão usadas na tela
    public Integer getId() {
        return cattle.getId();
    }

    public Integer getNumbering() {
        return cattle.getNumbering();
    }

    public Float getWeight() {
        return cattle.getWeight();
    }

    public Float getPrice() {
        return cattle.getPrice();
    }

    public String getInfo() {
        return cattle.getInfo(); 
    }

    public Boolean getDeleted() {
        return cattle.getDeleted();
    }

    public String getIdFarm() {
        return this.farmRegisterNumber;
    }

    public void setId(Integer id){
        cattle.setId(id);
    }

    public void setNumbering(Integer numbering) {
        cattle.setNumbering(numbering);
    }

    public void setWeight(Float weight) {
        cattle.setWeight(weight);
    }

    public void setPrice(Float price) {
        cattle.setPrice(price);
    }

    public void setInfo(String info){
        cattle.setInfo(info);
    }

    public void setDeleted(Boolean deleted) {
        cattle.setDeleted(deleted);
    }

    public void setIdFarm(String idFarm) {
        this.farmRegisterNumber= idFarm;
    }



    // Ações disponibilizadas na tela
    public void save() {
        Farm farm = farmService.findByRegisterNumber(this.farmRegisterNumber);

        this.cattle = Cattle.builder()
                            .id(getId())
                            .weight(getWeight())
                            .price(getPrice())
                            .numbering(getNumbering())
                            .info(getInfo())
                            .deleted(false)
                            .farm(farm)
        					.build();
               
        String msg;
        try {
            if (getId() == null) {
                cattleService.create(this.cattle);
                msg = "O boi " + (getNumbering()).toString() + " foi criada com sucesso!";
            } else {
                cattleService.update(this.cattle);
                msg = "O boi " + (getNumbering()).toString() + " foi atualizado com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Um boi com a numeração " + (getNumbering()).toString() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        cattle = new Cattle();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.cattle = cattleService.readById(getId());
            try {
                setDeleted(true);
                cattleService.update(cattle);
                
                msg = "O boi " + (getNumbering()).toString() + " foi removido com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("cattle-list.jsf");
        } else {
            throw new BusinessException("Não existe boi para ser deletado!");
        }
    }

    

    // Funções auxiliares
    public boolean isNew() {
        return cattle == null || getId() == null;
    }

    public List<String> completeFarm(String query) {
        List<String> result = farmService.getRegisterNumbers(query);
        return result;
    }
    
}
