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

import br.com.cattle_control.starter.model.Action;
import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.model.PaymentType;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.ActionService;
import br.com.cattle_control.starter.service.PeopleService;
import br.com.cattle_control.starter.service.PlaceService;
import br.com.cattle_control.starter.service.PaymentTypeService;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class ActionFormView {
    @Autowired
    private final ActionService actionService;

    @Autowired
    private final PeopleService peopleService;

    @Autowired
    private final PlaceService placeService;

    @Autowired
    private final PaymentTypeService paymentTypeService;

    private Action action = new Action();

    private String idPeople_buyer;
    private String idPeople_seller;
    private String idPeople_deliveryman;

    private String idPlace;

    private String idPaymentType;

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String actionId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (actionId != null) {

            int id = Integer.parseInt(actionId); 
            action = actionService.readById(id);

            this.idPeople_buyer = action.getPeople_buyer().getIdType();
            this.idPeople_seller = action.getPeople_seller().getIdType();
            this.idPeople_deliveryman = action.getPeople_deliveryman().getIdType();

    
            this.idPlace = action.getPlace().getCep();

            this.idPaymentType = action.getPayment_type().getPaymentType_name();

        } 
    }


    // Delegação de Getters and Setters das propriedades de farm que serão usadas na tela
    public Integer getId() {
        return action.getId();
    }

    public void setId(Integer id){
        this.action.setId(id);
    }

    public String getIdPeople_buyer() {
        return idPeople_buyer;
    }

    public void setIdPeople_buyer(String idPeople_buyer) {
        this.idPeople_buyer = idPeople_buyer;
    }

    public String getIdPeople_seller() {
        return idPeople_seller;
    }

    public void setIdPeople_seller(String idPeople_seller) {
        this.idPeople_seller = idPeople_seller;
    }

    public String getIdPeople_deliveryman() {
        return idPeople_deliveryman;
    }

    public void setIdPeople_deliveryman(String idPeople_deliveryman) {
        this.idPeople_deliveryman = idPeople_deliveryman;
    }

    public Integer getAmount() {
        return action.getAmount();
    }

    public void setAmount(Integer amount) {
        this.action.setAmount(amount);
    }

    public Double getValue() {
        return action.getValue();
    }

    public void setValue(Double value) {
        this.action.setValue(value);
    }

    public String getDate() {
        return action.getDate();
    }

    public void setDate(String date) {
        this.action.setDate(date);
    }

    public String getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(String idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public PaymentType getPayment_type() {
        return action.getPayment_type();
    }

    public void setPayment_type(PaymentType payment_type) {
        this.action.setPayment_type(payment_type);
    }

    public Integer getDivided() {
        return action.getDivided();
    }

    public void setDivided(Integer divided) {
        this.action.setDivided(divided);
    }

    public String getDeadline() {
        return action.getDeadline();
    }

    public void setDeadline(String deadline) {
        this.action.setDeadline(deadline);
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getDistrict() {
        return action.getDistrict();
    }

    public void setDistrict(String district) {
        this.action.setDistrict(district);
    }

    public String getReference() {
        return action.getReference();
    }

    public void setReference(String reference) {
        this.action.setReference(reference);
    }

    public Integer getNumber() {
        return action.getNumber();
    }

    public void setNumber(Integer number) {
        this.action.setNumber(number);
    }

    public String getInfo() {
        return action.getInfo();
    }

    public void setInfo(String info) {
        this.action.setInfo(info);
    }

    public Boolean getDeleted() {
        return action.getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        this.action.setDeleted(deleted);
    }



    // Ações disponibilizadas na tela
    public void save() {
        People people_buyer = peopleService.findByIdType(this.idPeople_buyer);
        People people_seller = peopleService.findByIdType(this.idPeople_seller);
        People people_deliveryman = peopleService.findByIdType(this.idPeople_deliveryman);

        Place place = placeService.findByCep(this.idPlace);
        PaymentType paymentType;

        System.out.println("Estou aqui: " + this.idPaymentType + " -----------------------------");
        if(this.idPaymentType != null){
            paymentType = paymentTypeService.findByPaymentTypeName(this.idPaymentType);
        }
        else {
            System.out.println("Estou aqui2");
            paymentType = paymentTypeService.findByPaymentTypeName("Cheque");
        }

        String dateFormat = getDate().substring(8, 10) + "/" + findNumericMonth(getDate().substring(4, 7)) + "/" + getDate().substring(26, 28);
        String deadlineFormat = getDeadline().substring(8, 10) + "/" + findNumericMonth(getDeadline().substring(4, 7)) + "/" + getDeadline().substring(26, 28);

        this.action = Action.builder()
                                    .id(getId())
                                    .people_buyer(people_buyer)
                                    .people_seller(people_seller)
                                    .people_deliveryman(people_deliveryman)
                                    .amount(getAmount())
                                    .value(getValue())
                                    .date(dateFormat)
                                    .payment_type(paymentType)
                                    .divided(getDivided())
                                    .deadline(deadlineFormat)
                                    .place(place)
                                    .district(getDistrict())
                                    .reference(getReference())
                                    .number(getNumber())
                                    .info(getInfo())
                                    .deleted(false)
                                    .build();
               
        String msg;
        try {
            if (getId() == null) {
                actionService.create(this.action);
                msg = "A ação " + getId() + " foi criada com sucesso!";
            } else {
                actionService.update(this.action);
                msg = "A ação " + getId() + " foi atualizada com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Uma ação com o número de identificação " + getId() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        action = new Action();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.action = actionService.readById(getId());
            try {
                setDeleted(true);
                actionService.update(action);
                
                msg = "A ação " + getId() + " foi removida com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("action-list.jsf");
        } else {
            throw new BusinessException("Não existe ação para ser deletada!");
        }
    }


    // Funções auxiliares
    public boolean isNew() {
        return action == null || getId() == null;
    }

    public List<String> completeIdTypeBuyer(String query) {
        List<String> result = peopleService.getIDsTypeBuyer(query);
        return result;
    }

    public List<String> completeIdTypeSeller(String query) {
        List<String> result = peopleService.getIDsTypeSeller(query);
        return result;
    }

    public List<String> completeIdTypeDeliveryman(String query) {
        List<String> result = peopleService.getIDsTypeDeliveryman(query);
        return result;
    }

    public List<String> completeCep(String query) {
        List<String> result = placeService.getCeps(query);
        return result;
    }

    public List<PaymentType> listAllPaymentTypes() {
        List<PaymentType> result = paymentTypeService.readAll();

        System.out.println(result);
        return result;
    }

    public String findNumericMonth(String month){
        switch (month.toLowerCase()) {
            case "jan":
                return "01";

            case "feb":
                return "02";

            case "mar":
                return "03";

            case "apr":
                return "04";

            case "may":
                return "05";

            case "jun":
                return "06";

            case "jul":
                return "07";

            case "aug":
                return "08";

            case "sep":
                return "09";

            case "oct":
                return "10";

            case "nov":
                return "11";

            default:
                return "12";
        }
    }
    
}
