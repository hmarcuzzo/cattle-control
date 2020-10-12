package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

// import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Action;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.ActionRepository;


@Service
@RequiredArgsConstructor
public class ActionService implements ICRUDService<Action> {
    private final ActionRepository actionRepository;

    public List<Action> readAll() {
        return actionRepository
                            .findAll()
                            .stream()
                            .filter(currentAction -> currentAction.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public Action readById(Integer anId) throws EntityNotFoundException {
        return actionRepository
        .findAll()
        .stream()
        .filter(currentAction -> currentAction.getDeleted().equals(false) && currentAction.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public Action create(Action anAction) throws EntityAlreadyExistsException, AnyPersistenceException {

        try {
            return actionRepository.save(anAction);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public Action update(Action entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		
		try {
            return actionRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

    @Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }
}
