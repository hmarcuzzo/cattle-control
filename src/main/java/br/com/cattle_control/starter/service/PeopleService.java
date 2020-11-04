package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.PeopleRepository;
import br.com.cattle_control.starter.service.RoleService;


@Service
@RequiredArgsConstructor
public class PeopleService implements ICRUDService<People> {
    private final PeopleRepository peopleRepository;
    private final RoleService roleService;

    public List<People> readAll() {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> getIDsType(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getIdType().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false))
                            .map(People::getIdType)
                            .collect(Collectors.toList());
    }

    public List<String> getIDsTypeBuyer(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getIdType().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false) && currentPeople.getRoles().contains(roleService.findByRoleName("Comprador")))
                            .map(People::getIdType)
                            .collect(Collectors.toList());
    }

    public List<String> getIDsTypeSeller(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getIdType().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false) && currentPeople.getRoles().contains(roleService.findByRoleName("Vendedor")))
                            .map(People::getIdType)
                            .collect(Collectors.toList());
    }

    public List<String> getIDsTypeDeliveryman(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getIdType().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false) && currentPeople.getRoles().contains(roleService.findByRoleName("Freteiro")))
                            .map(People::getIdType)
                            .collect(Collectors.toList());
    }

    public List<String> getIDsTypeVet(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getIdType().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false) && currentPeople.getRoles().contains(roleService.findByRoleName("Veterinário")))
                            .map(People::getIdType)
                            .collect(Collectors.toList());
    }

    public List<String> getNames(String query) {
        return peopleRepository
                            .findAll()
                            .stream()
                            .filter(currentPeople -> currentPeople.getName().toLowerCase().contains(query.toLowerCase()) && currentPeople.getDeleted().equals(false))
                            .map(People::getName)
                            .collect(Collectors.toList());
    }

    public People readById(Integer anId) throws EntityNotFoundException {
        return peopleRepository
        .findAll()
        .stream()
        .filter(currentPeople -> currentPeople.getDeleted().equals(false) && currentPeople.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public People create(People anPeople) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (peopleRepository
        		.findAll()
                .stream()
                .filter(currentPeople -> currentPeople.getDeleted().equals(false))
        		.anyMatch(currentPeople -> currentPeople.getIdType().equals(anPeople.getIdType()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return peopleRepository.save(anPeople);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public People update(People entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (peopleRepository
        		.findAll()
                .stream()
                .filter(currentPeople -> currentPeople.getDeleted().equals(false) && !currentPeople.getId().equals(entity.getId()))
        		.anyMatch(currentPeople -> currentPeople.getIdType().equals(entity.getIdType()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return peopleRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public People findByIdType(String idType) {
        return peopleRepository.findAll()
                .stream()
                .filter(currentPeople -> currentPeople.getIdType().equals(idType) && currentPeople.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Pessoa com CPF/CNPJ " + idType + " não encontrada"));
    }

}