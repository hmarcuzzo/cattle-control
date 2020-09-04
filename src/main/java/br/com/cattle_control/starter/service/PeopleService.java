package br.com.cattle_control.starter.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.PeopleRepository;

@Service
@RequiredArgsConstructor
public class PeopleService implements ICRUDService<People>{
    private final PeopleRepository peopleRepository;

    public List<People> readAll() { // need to fix
        return List.copyOf(peopleRepository.findAll());
    }

    public List<String> getIDsType(String query) {
        return null;
    }

    public List<String> getNames(String query) {
        return null;
    }

    public People readById(Integer anId) throws EntityNotFoundException { // need to fix
        return peopleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
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