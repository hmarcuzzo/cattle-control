package br.com.cattle_control.starter.view;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.CattleExpense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.CattleExpenseService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

@Component
@RequestScope
@RequiredArgsConstructor
public class CattleExpenseListView {
    @Autowired
    private final CattleExpenseService cattleExpenseService;
    
    private CattleExpense cattleExpense = new CattleExpense();

    LazyDataModel<CattleExpense> cattlesExpenses;

    Filter<CattleExpense> filter = new Filter<>(new CattleExpense());

    List<CattleExpense> selectedCattleExpenses;    //cattleExpenses selected in checkbox column

    List<CattleExpense> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<CattleExpense> getSelectedCattleExpenses() {
        return selectedCattleExpenses;
    }

    public void setSelectedCattleExpenses(List<CattleExpense> selectedCattleExpenses) {
        this.selectedCattleExpenses = selectedCattleExpenses;
    }

    public List<CattleExpense> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<CattleExpense> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<CattleExpense> getCattlesExpenses() {
        return cattlesExpenses;
    }

    public void setCattlesExpenses(LazyDataModel<CattleExpense> cattlesExpenses) {
        this.cattlesExpenses = cattlesExpenses;
    }

    public Filter<CattleExpense> getFilter() {
        return filter;
    }

    public void setFilter(Filter<CattleExpense> filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return cattleExpense.getId();
    }

    public void setId(Integer id) {
        this.cattleExpense.setId(id);
    }

    // Ações disponibilizadas na tela
    public void delete() {
        int numCattlesExpense = 0;
        String msg = "";
        if (!selectedCattleExpenses.isEmpty()) {
            for (CattleExpense selectedCattleExpense : selectedCattleExpenses) {
                

                try {
                    numCattlesExpense++;
                    selectedCattleExpense.setDeleted(true);
                    cattleExpenseService.update(selectedCattleExpense);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }
    
    
            }
    
            selectedCattleExpenses.clear();
            if (msg == "") {
                addDetailMessage(numCattlesExpense + " gastos de bois foram deletados com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numCattlesExpense + " gastos de bois foram deletados com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione algum gasto de boi para ser deletado!");
        }
        
    }

    public void findCattleExpenseById(Integer anId) {
        if (anId == null) {
            throw new BusinessException("Coloque um número de registro para procurar!");
        }
        
        try {
            selectedCattleExpenses.add(cattleExpenseService.readById(anId));
        }
        catch (final EntityNotFoundException e){
                throw new BusinessException("Gasto de boi com número de identificação " + anId + " não foi encontrado");
        }
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<CattleExpense>(new CattleExpense());
    }

    public List<CattleExpense> listAllCattleExpense() {
        return cattleExpenseService.readAll();
    }
}
