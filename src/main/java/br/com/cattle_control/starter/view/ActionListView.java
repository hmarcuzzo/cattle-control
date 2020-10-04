package br.com.cattle_control.starter.view;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder.In;

import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Action;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.ActionService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

@Component
@RequestScope
@RequiredArgsConstructor
public class ActionListView {
    @Autowired
    private final ActionService actionService;
    
    private Action action = new Action();

    LazyDataModel<Action> actions;

    Filter<Action> filter = new Filter<>(new Action());

    List<Action> selectedActions;    //actions selected in checkbox column

    List<Action> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<Action> getSelectedActions() {
        return selectedActions;
    }

    public void setSelectedActions(List<Action> selectedActions) {
        this.selectedActions = selectedActions;
    }

    public List<Action> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Action> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<Action> getActions() {
        return actions;
    }

    public void setActions(LazyDataModel<Action> actions) {
        this.actions = actions;
    }

    public Filter<Action> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Action> filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return action.getId(); 
    }

    public void setId(Integer id){
        this.action.setId(id);
    }



    // Ações disponibilizadas na tela
    public void delete() {
        int numActions = 0;
        String msg = "";
        if (!selectedActions.isEmpty()) {
            for (Action selectedAction : selectedActions) {

                try {
                    numActions++;
                    selectedAction.setDeleted(true);
                    actionService.update(selectedAction);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }    
            }
    
            selectedActions.clear();
            if (msg == "") {
                addDetailMessage(numActions + " ações de compra e venda foram deletadas com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numActions + " ações de compras e vendas foram deletadas com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione alguma ação de compra e venda para ser deletada!");
        }
        
    }

    public void findActionById(Integer anId) {
        if (anId == null) {
            throw new BusinessException("Coloque um ID de ação de compra e venda para procurar!");
        }
        try {
            selectedActions.add(actionService.readById(anId));
        }
        catch (final EntityNotFoundException e){
            throw new BusinessException("Ação de venda e compra com número de identificação " + anId + " não foi encontrada");
        }
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<Action>(new Action());
    }

    public List<Action> listAllAction() {
        return actionService.readAll();
    }

}
