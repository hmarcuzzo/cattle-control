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


import br.com.cattle_control.starter.model.CattleExpense;
import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.model.Expense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.CattleExpenseService;
import br.com.cattle_control.starter.service.CattleService;
import br.com.cattle_control.starter.service.ExpenseService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class CattleExpenseFormView {
    @Autowired
    private final CattleExpenseService cattleExpenseService;

    @Autowired
    private final CattleService cattleService;

    @Autowired
    private final ExpenseService expenseService;

    

    private CattleExpense cattleExpense = new CattleExpense();

    private String idCattle;
    private Integer idExpense;


    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String cattleExpenseId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (cattleExpenseId != null) {

            int id = Integer.parseInt(cattleExpenseId); 
            cattleExpense = cattleExpenseService.readById(id);

            this.idCattle = cattleExpense.getCattle().getNumbering();
            this.idExpense = cattleExpense.getExpense().getId();

        } 
    }


    // Delegação de Getters and Setters das propriedades de farm que serão usadas na tela
    public Integer getId() {
        return cattleExpense.getId();
    }

    public void setId(Integer id){
        this.cattleExpense.setId(id);
    }

    public String getDate() {
        return cattleExpense.getDate();
    }

    public void setDate(String date) {
        this.cattleExpense.setDate(date);
    }

    public String getInfo() {
        return cattleExpense.getInfo();
    }

    public void setInfo(String info) {
        this.cattleExpense.setInfo(info);
    }

    public Cattle getCattle() {
        return cattleExpense.getCattle();
    }

    public void setCattle(Cattle cattle) {
        this.cattleExpense.setCattle(cattle);
    }

    public Expense getExpense() {
        return cattleExpense.getExpense();
    }

    public void setExpense(Expense expense) {
        this.cattleExpense.setExpense(expense);
    }

    public String getIdCattle() {
        return idCattle;
    }

    public void setIdCattle(String idCattle) {
        this.idCattle = idCattle;
    }

    public Integer getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(Integer idExpense) {
        this.idExpense = idExpense;
    }

    public Boolean getDeleted() {
        return cattleExpense.getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        this.cattleExpense.setDeleted(deleted);
    }


    // Ações disponibilizadas na tela
    public void save() {
        setCattle(cattleService.findByNumbering(this.idCattle));
        setExpense(expenseService.readById(this.idExpense));


        String dateFormat = getDate().substring(8, 10) + "/" + findNumericMonth(getDate().substring(4, 7)) + "/" + getDate().substring(26, 28);

        this.cattleExpense = CattleExpense.builder()
                                .id(getId())
                                .date(dateFormat)
                                .info(getInfo())
                                .cattle(getCattle())
                                .expense(getExpense())
                                .deleted(false)
                                .build();
                
        String msg;
        try {
            if (getId() == null) {
                cattleExpenseService.create(this.cattleExpense);
                msg = "O gasto de boi " + getId() + " foi criado com sucesso!";
            } else {
                cattleExpenseService.update(this.cattleExpense);
                msg = "O gasto de boi " + getId() + " foi atualizado com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Um gasto de boi com o número de identificação " + getId() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        cattleExpense = new CattleExpense();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.cattleExpense = cattleExpenseService.readById(getId());
            try {
                setDeleted(true);
                cattleExpenseService.update(cattleExpense);
                
                msg = "O gasto do boi " + getId() + " foi removido com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("cattleExpense-list.jsf");
        } else {
            throw new BusinessException("Não existe gasto de boi para ser deletado!");
        }
    }


    // Funções auxiliares
    public boolean isNew() {
        return cattleExpense == null || getId() == null;
    }

    public List<String> completeCattleNumbering(String query) {
        List<String> result = cattleService.getNumberings(query);
        
        return result;
    }

    public List<Integer> completeExpenseNames(String query) {
        List<Integer> result = expenseService.getIds(query);
        
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
