package br.com.cattle_control.starter.view;

import org.omnifaces.util.Faces;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import javax.faces.context.FacesContext;

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
import br.com.cattle_control.starter.service.FarmService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

/**
* @autor Henrique Marcuzzo
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class CattleExpenseFormFarmView {
    @Autowired
    private final CattleExpenseService cattleExpenseService;

    @Autowired
    private final CattleService cattleService;

    @Autowired
    private final ExpenseService expenseService;

    @Autowired
    private final FarmService farmService;

    

    private CattleExpense cattleExpense = new CattleExpense();

    private String  idFarm;
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

            this.idFarm = cattleExpense.getCattle().getFarm().getRegisterNumber();
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

    public String getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(String idFarm) {
        this.idFarm = idFarm;
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
    public void save() throws IOException {
        String dateFormat = getDate().substring(8, 10) + "/" + findNumericMonth(getDate().substring(4, 7)) + "/" + getDate().substring(26, 28);
        
        List<Cattle> allCattle = cattleService.findAllCattleFromFarm(this.idFarm);
        
        String msg = "";
        Integer numAdd = 0;
        
        for (Cattle cattle : allCattle) {
            setCattle(cattle);
            setExpense(expenseService.readById(this.idExpense));
    
            this.cattleExpense = CattleExpense.builder()
                                    .date(dateFormat)
                                    .info(getInfo())
                                    .cattle(getCattle())
                                    .expense(getExpense())
                                    .deleted(false)
                                    .build();
                    
            try {
                cattleExpenseService.create(this.cattleExpense);
                numAdd++;
                clear();
            } catch (final EntityAlreadyExistsException e) {
                msg = "Erro Inesperado!!";
                break;
    
            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";
                break;
    
            }
            
            
        }

        if (msg == "" || msg.isEmpty()) {
            msg = "Foram criados " + numAdd + " gastos de boi com sucesso!";
        }
        else{
            msg += " Apenas " + numAdd + " gastos de boi foram criados com sucesso!";
        }
        
        

        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("cattleExpense-list.jsf");
    }

    public void clear() {
        this.cattleExpense = new CattleExpense();
    }

    // public void delete() throws IOException {
    //     String msg;
    //     if (has(getId())) {
    //         this.cattleExpense = cattleExpenseService.readById(getId());
    //         try {
    //             setDeleted(true);
    //             cattleExpenseService.update(cattleExpense);
                
    //             msg = "O gasto do boi " + getId() + " foi removido com sucesso";

    //         } catch (final AnyPersistenceException e) {
    //             msg = "Erro na gravação dos dados!";

    //         } catch(final EntityAlreadyExistsException e) {
    //             msg = "Erro inesperado!";
    //         }
            
    //         addDetailMessage(msg);
            
    //         Faces.getFlash().setKeepMessages(true);
    //         Faces.redirect("cattleExpense-list.jsf");
    //     } else {
    //         throw new BusinessException("Não existe gasto de boi para ser deletado!");
    //     }
    // }


    // Funções auxiliares
    public boolean isNew() {
        return cattleExpense == null || getId() == null;
    }

    public List<String> completeFarmNumbering(String query) {
        List<String> result = farmService.getRegisterNumbers(query);
        
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
