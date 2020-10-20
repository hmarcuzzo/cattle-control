package br.com.cattle_control.starter.view;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Expense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.ExpenseService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

@Component
@RequestScope
@RequiredArgsConstructor
public class ExpenseListView {
    @Autowired
    private final ExpenseService expenseService;
    
    private Expense expense = new Expense();

    LazyDataModel<Expense> expenses;

    Filter<Expense> filter = new Filter<>(new Expense());

    List<Expense> selectedExpenses;    //expenses selected in checkbox column

    List<Expense> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<Expense> getSelectedExpenses() {
        return selectedExpenses;
    }

    public void setSelectedExpenses(List<Expense> selectedExpenses) {
        this.selectedExpenses = selectedExpenses;
    }

    public List<Expense> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Expense> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(LazyDataModel<Expense> expenses) {
        this.expenses = expenses;
    }

    public Filter<Expense> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Expense> filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return expense.getId(); 
    }

    public void setId(Integer id){
        this.expense.setId(id);
    }


    // Ações disponibilizadas na tela
    public void delete() {
        int numExpenses = 0;
        String msg = "";
        if (!selectedExpenses.isEmpty()) {
            for (Expense selectedExpense : selectedExpenses) {

                try {
                    numExpenses++;
                    selectedExpense.setDeleted(true);
                    expenseService.update(selectedExpense);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }    
            }
    
            selectedExpenses.clear();
            if (msg == "") {
                addDetailMessage(numExpenses + " gastos foram deletados com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numExpenses + " gastos foram deletados com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione algum gasto para ser deletado!");
        }
        
    }

    public void findExpenseById(Integer anId) {
        if (anId == null) {
            throw new BusinessException("Coloque um ID de gasto para procurar!");
        }
        try {
            selectedExpenses.add(expenseService.readById(anId));
        }
        catch (final EntityNotFoundException e){
            throw new BusinessException("Gasto com número de identificação " + anId + " não foi encontrado");
        }
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<Expense>(new Expense());
    }

    public List<Expense> listAllExpense() {
        return expenseService.readAll();
    }
}
