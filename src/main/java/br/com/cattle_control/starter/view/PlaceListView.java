package br.com.cattle_control.starter.view;

import java.util.List;


import lombok.RequiredArgsConstructor;
	

import org.primefaces.model.LazyDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PlaceService;
import br.com.cattle_control.starter.infra.model.Filter;

import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;


@Component
@RequestScope
@RequiredArgsConstructor
public class PlaceListView {
    
    @Autowired
    private final PlaceService placeService;

    private Place place = new Place();

    LazyDataModel<Place> places;

    Filter<Place> filter = new Filter<>(new Place());

    List<Place> selectedPlaces;    //places selected in checkbox column

    List<Place> filteredValue;     // datatable filteredValue attribute (column filters)



    // Delegação de Getters and Setters das propriedades de place que serão usadas na tela
    public List<Place> getSelectedPlaces() {
        return selectedPlaces;
    }

    public void setSelectedPlaces(List<Place> selectedPlaces) {
        this.selectedPlaces = selectedPlaces;
    }

    public List<Place> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Place> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public String getCep() {
        return place.getCep(); 
    }

    public void setCep(String cep){
        this.place.setCep(cep);
    }

    public LazyDataModel<Place> getPlaces() {
        return places;
    }

    public void setPlaces(LazyDataModel<Place> places) {
        this.places = places;
    }

    public Filter<Place> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Place> filter) {
        this.filter = filter;
    }



    // Ações disponibilizadas na tela
    public void delete() {
        int numPlaces = 0;
        String msg = "";
        if (!selectedPlaces.isEmpty()) {
            for (Place selectedPlace : selectedPlaces) {
                

                try {
                    numPlaces++;
                    selectedPlace.setDeleted(true);
                    placeService.update(selectedPlace);
    
                } catch (final EntityAlreadyExistsException e) {
                    msg = "Erro inesperado!";
                    break;
    
                } catch (final AnyPersistenceException e) {
                    msg = "Erro na gravação dos dados!";
                    break;
    
                }
    
    
            }
    
            selectedPlaces.clear();
            if (msg == "") {
                addDetailMessage(numPlaces + " locais foram deletados com sucesso!");
            } else {
                addDetailMessage(msg + " Apenas " + numPlaces + " locais foram deletados com sucesso!");
            }
        } else {
            throw new BusinessException("Selecione algum local para ser deletado!");
        } 
    }

    public void findPlaceByCep(String cep) {
        if (cep == null) {
            throw new BusinessException("Coloque um CPF/CNPJ para procurar!");
        }
        selectedPlaces.add(placeService.findByCep(cep));
    }



    // Funções auxiliares
    public void clear() {
        filter = new Filter<Place>(new Place());
    }

    public List<Place> listAllPlace() {
        return placeService.readAll();
    }
}
