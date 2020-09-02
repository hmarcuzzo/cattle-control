package br.com.cattle_control.starter.bean;

import javax.faces.view.ViewScoped;
import org.omnifaces.util.Faces;

import java.io.Console;
// import javax.inject.Inject;
// import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

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

public class PeopleListMB implements Serializable {
    
    
}