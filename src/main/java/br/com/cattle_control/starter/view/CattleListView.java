package br.com.cattle_control.starter.view;


import java.util.List;


import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.CattleService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

@Component
@RequestScope
@RequiredArgsConstructor
public class CattleListView {
    
    @Autowired
    private final CattleService cattleService;
    
    private Cattle cattle = new Cattle();

    LazyDataModel<Cattle> cattles;

    Filter<Cattle> filter = new Filter<>(new Cattle());

    List<Cattle> selectedCattles;    //cattles selected in checkbox column

    List<Cattle> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<Cattle> getSelectedCattles() {
        return selectedCattles;
    }

    public void setSelectedCattles(List<Cattle> selectedCattles) {
        this.selectedCattles = selectedCattles;
    }

    public List<Cattle> getFilteredValue() {
        return filteredValue;
    }

    public String getNumbering() {
        return cattle.getNumbering(); 
    }

    public void setFilteredValue(List<Cattle> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<Cattle> getCattles() {
        return cattles;
    }

    public void setCattles(LazyDataModel<Cattle> cattles) {
        this.cattles = cattles;
    }

    public Filter<Cattle> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Cattle> filter) {
        this.filter = filter;
    }

    public void setNumbering(String numbering){
        cattle.setNumbering(numbering);
    }   

    // Ações disponibilizadas na tela
    public void delete() {
        int numCattles = 0;
        String msg = "";
        if (!selectedCattles.isEmpty()) {
            for (Cattle selectedCattle : selectedCattles) {
                

                try {
                    numCattles++;
                    selectedCattle.setDeleted(true);
                    cattleService.update(selectedCattle);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }
    
    
            }
    
            selectedCattles.clear();
            if (msg == "") {
                addDetailMessage(numCattles + " bois foram deletados com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numCattles + " bois foram deletados com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione algum boi para ser deletado!");
        }
        
    }

    public void findCattleByNumbering(String numbering) {
        if (numbering == null) {
            throw new BusinessException("Coloque uma numeração para procurar!");
        }
        selectedCattles.add(cattleService.findByNumbering(numbering));
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<Cattle>(new Cattle());
    }

    public List<String> completeRegisterNumber(String query) {
        List<String> result = cattleService.getNumberings(query);
        return result;
    }

    public List<Cattle> listAllCattle() {
        return cattleService.readAll();
    }
}