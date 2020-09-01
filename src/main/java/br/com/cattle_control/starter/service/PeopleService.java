package br.com.cattle_control.starter.service;

// import java.net.URLConnection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

// import org.aspectj.weaver.ast.And;
import org.springframework.stereotype.Service;

import br.com.cattle_control.starter.model.People;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.PeopleRepository;

@Service
@RequiredArgsConstructor
public class PeopleService implements ICRUDService<People>{
    private final PeopleRepository peopleRepository;

    public List<People> readAll() {
        return List.copyOf(peopleRepository.findAll());
    }

    public People readById(Integer anId) throws EntityNotFoundException {
        return peopleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public People create(People anPeople) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (peopleRepository
        		.findAll()
        		.stream()
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
        		.anyMatch(currentPeople -> currentPeople.getIdType().equals(entity.getIdType()) && !currentPeople.getId().equals(entity.getId()))) {
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

}