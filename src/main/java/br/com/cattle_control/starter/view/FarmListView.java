package br.com.cattle_control.starter.view;


import java.util.List;


import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.FarmService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;

@Component
@RequestScope
@RequiredArgsConstructor
public class FarmListView {
    
    @Autowired
    private final FarmService farmService;
    
    private Farm farm = new Farm();

    LazyDataModel<Farm> farms;

    Filter<Farm> filter = new Filter<>(new Farm());

    List<Farm> selectedFarms;    //farms selected in checkbox column

    List<Farm> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<Farm> getSelectedFarms() {
        return selectedFarms;
    }

    public void setSelectedFarms(List<Farm> selectedFarms) {
        this.selectedFarms = selectedFarms;
    }

    public List<Farm> getFilteredValue() {
        return filteredValue;
    }

    public String getRegisterNumber() {
        return farm.getRegisterNumber(); 
    }

    public void setFilteredValue(List<Farm> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<Farm> getFarms() {
        return farms;
    }

    public void setFarms(LazyDataModel<Farm> farms) {
        this.farms = farms;
    }

    public Filter<Farm> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Farm> filter) {
        this.filter = filter;
    }

    public void setRegisterNumber(String resgisterNumber){
        farm.setRegisterNumber(resgisterNumber);
    }   

    // Ações disponibilizadas na tela
    public void delete() {
        int numFarms = 0;
        String msg = "";
        if (!selectedFarms.isEmpty()) {
            for (Farm selectedFarm : selectedFarms) {
                

                try {
                    numFarms++;
                    selectedFarm.setDeleted(true);
                    farmService.update(selectedFarm);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }
    
    
            }
    
            selectedFarms.clear();
            if (msg == "") {
                addDetailMessage(numFarms + " fazendas foram deletadas com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numFarms + " fazendas foram deletadas com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione alguma fazenda para ser deletada!");
        }
        
    }

    public void findFarmByRegisterNumber(String registerNumber) {
        if (registerNumber == null) {
            throw new BusinessException("Coloque um número de registro para procurar!");
        }
        selectedFarms.add(farmService.findByRegisterNumber(registerNumber));
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<Farm>(new Farm());
    }

    public List<String> completeRegisterNumber(String query) { // TO DO
        List<String> result = farmService.getRegisterNumbers(query);
        return result;
    }

    public List<String> completeName(String query) { // TO DO
        List<String> result = farmService.getNames(query);
        return result;
    }

    public List<Farm> listAllFarm() {
        return farmService.readAll();
    }
}