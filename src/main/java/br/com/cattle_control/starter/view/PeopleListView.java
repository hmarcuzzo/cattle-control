package br.com.cattle_control.starter.view;

import javax.faces.view.ViewScoped;
import org.omnifaces.util.Faces;

import java.io.Console;
// import javax.inject.Inject;
// import javax.inject.Named;
import java.io.IOException;

import java.util.List;
import java.util.Map;
// import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;


import lombok.RequiredArgsConstructor;
	
// import org.primefaces.model.DefaultStreamedContent;
// import org.primefaces.model.StreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.service.PeopleService;
import br.com.cattle_control.starter.infra.model.Filter;
import com.github.adminfaces.template.exception.BusinessException;


import static br.com.cattle_control.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

@Component
@RequestScope
@RequiredArgsConstructor
public class PeopleListView {
    
    @Autowired
    private final PeopleService peopleService;
    
    private People people = new People();

    LazyDataModel<People> peoples;

    Filter<People> filter = new Filter<>(new People());

    List<People> selectedPeoples;    //cars selected in checkbox column

    List<People> filteredValue;     // datatable filteredValue attribute (column filters)


    public List<People> getSelectedPeoples() {
        return selectedPeoples;
    }

    public void setSelectedPeoples(List<People> selectedPeoples) {
        this.selectedPeoples = selectedPeoples;
    }

    public List<People> getFilteredValue() {
        return filteredValue;
    }

    public String getIdType() {
        return people.getIdType(); 
    }

    public void setFilteredValue(List<People> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public LazyDataModel<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(LazyDataModel<People> peoples) {
        this.peoples = peoples;
    }

    public Filter<People> getFilter() {
        return filter;
    }

    public void setFilter(Filter<People> filter) {
        this.filter = filter;
    }

    public void setIdType(String idType){
        people.setIdType(idType);
    }
    

    // Ações disponibilizadas na tela
    public void delete() {
        System.out.println("Estou aqui!");
        int numPeoples = 0;
        String msg = "";
        for (People selectedPeople : selectedPeoples) {
            

            try {
                numPeoples++;
                selectedPeople.setDeleted(true);
                peopleService.update(selectedPeople);

            } catch (final EntityAlreadyExistsException e) {
                msg = "Erro inesperado!";
                break;

            } catch (final AnyPersistenceException e) {
                msg = "Erro na gravação dos dados!";
                break;

            }


        }

        selectedPeoples.clear();
        if (msg == "") {
            addDetailMessage(numPeoples + " pessoas foram deletadas com sucesso!");
        } else {
            addDetailMessage(msg + " Apenas " + numPeoples + " pessoas foram deletadas com sucesso!");
        }
    }

    public void findPeopleByIdType(String idType) {
        if (idType == null) {
            throw new BusinessException("Coloque um CPF/CNPJ para procurar!");
        }
        selectedPeoples.add(peopleService.findByIdType(idType));
        System.out.println(selectedPeoples);
    }


    // Funções auxiliares
    public void clear() {
        filter = new Filter<People>(new People());
    }

    public List<String> completeIdType(String query) { // TO DO
        List<String> result = peopleService.getIDsType(query);
        return result;
    }

    public List<String> completeName(String query) { // TO DO
        List<String> result = peopleService.getNames(query);
        return result;
    }

    public List<People> listAllPeople() {
        return peopleService.readAll();
    }
}