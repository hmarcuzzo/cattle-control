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

import br.com.cattle_control.starter.model.Expense;
import br.com.cattle_control.starter.model.TypeExpense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.ExpenseService;
import br.com.cattle_control.starter.service.TypeExpenseService;

import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
* @autor Henrique Marcuzzo
*/

@Component
@RequestScope
@RequiredArgsConstructor
public class ExpenseFormView {
    @Autowired
    private final ExpenseService expenseService;

    @Autowired
    private final TypeExpenseService typeExpenseService;

    private Expense expense = new Expense();

    private String idTypeExpense;

    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    String expenseId = paramMap.get("id");

    public void init() {

        if(Faces.isAjaxRequest()){
           return;
        }
        if (expenseId != null) {

            int id = Integer.parseInt(expenseId); 
            expense = expenseService.readById(id);

            this.idTypeExpense = expense.getTypeExpense().getTypeExpense_name();

        } 
    }


    // Delegação de Getters and Setters das propriedades de farm que serão usadas na tela
    public Integer getId() {
        return expense.getId();
    }

    public void setId(Integer id){
        this.expense.setId(id);
    }

    public String getExpense_name() {
        return expense.getExpense_name();
    }

    public void setExpense_name(String expense_name) {
        this.expense.setExpense_name(expense_name);;
    }

    public Double getExpense_priceUnit() {
        return expense.getExpense_priceUnit();
    }

    public void setExpense_priceUnit(Double expense_priceUnit) {
        this.expense.setExpense_priceUnit(expense_priceUnit);;
    }

    public Integer getExpense_yield() {
        return expense.getExpense_yield();
    }

    public void setExpense_yield(Integer expense_yield) {
        this.expense.setExpense_yield(expense_yield);;
    }

    public String getIdTypeExpense() {
        return idTypeExpense;
    }

    public void setIdTypeExpense(String idTypeExpense) {
        this.idTypeExpense = idTypeExpense;
    }

    public TypeExpense getTypeExpense() {
        return expense.getTypeExpense();
    }

    public void setTypeExpense(TypeExpense typeExpense) {
        this.expense.setTypeExpense(typeExpense);;
    }

    public Boolean getDeleted() {
        return expense.getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        this.expense.setDeleted(deleted);
    }


    // Ações disponibilizadas na tela
    public void save() {
        setTypeExpense(typeExpenseService.findByTypeExpenseName(this.idTypeExpense));

        this.expense = Expense.builder()
                                .id(getId())
                                .expense_name(getExpense_name())
                                .expense_priceUnit(getExpense_priceUnit())
                                .expense_yield(getExpense_yield())
                                .typeExpense(getTypeExpense())
                                .deleted(false)
                                .build();
                
        String msg;
        try {
            if (getId() == null) {
                expenseService.create(this.expense);
                msg = "O gasto " + getId() + " foi criado com sucesso!";
            } else {
                expenseService.update(this.expense);
                msg = "O gasto " + getId() + " foi atualizado com sucesso!";
            }

        } catch (final EntityAlreadyExistsException e) {
            msg = "Um gasto com o número de identificação " + getId() + " já existe no Banco de Dados!";

        } catch (final AnyPersistenceException e) {
            msg = "Erro na gravação dos dados!";

        }

        addDetailMessage(msg);
    }

    public void clear() {
        expense = new Expense();
    }

    public void delete() throws IOException{
        String msg;
        if (has(getId())) {
            this.expense = expenseService.readById(getId());
            try {
                setDeleted(true);
                expenseService.update(expense);
                
                msg = "O gasto " + getId() + " foi removido com sucesso";

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";

            } catch(final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
            }
            
            addDetailMessage(msg);
            
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("expense-list.jsf");
        } else {
            throw new BusinessException("Não existe gasto para ser deletado!");
        }
    }


    // Funções auxiliares
    public boolean isNew() {
        return expense == null || getId() == null;
    }

    public List<String> listAllTypeExpenses() {
        List<String> result = typeExpenseService.readAllTypeExpenseName();

        return result;
    }
    
}
