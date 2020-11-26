package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

// import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.model.CattleExpense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.CattleExpenseRepository;

@Service
@RequiredArgsConstructor
public class CattleExpenseService implements ICRUDService<CattleExpense> {
    private final CattleExpenseRepository cattleExpenseRepository;

    public List<CattleExpense> readAll() {
        return cattleExpenseRepository
                    .findAll()
                    .stream()
                    .filter(currentCattleExpense -> currentCattleExpense.getDeleted().equals(false))
                    .collect(Collectors.toList());
    }

    public CattleExpense readById(Integer anId) throws EntityNotFoundException {
        return cattleExpenseRepository
                    .findAll()
                    .stream()
                    .filter(currentCattleExpense -> currentCattleExpense.getDeleted().equals(false) && currentCattleExpense.getId().equals(anId))
                    .findFirst()
                    .orElseThrow(EntityNotFoundException::new);
    }

    public List<CattleExpense> readByCattle(Cattle anCattle) {
        return cattleExpenseRepository
                    .findAll()
                    .stream()
                    .filter(currentCattleExpense -> currentCattleExpense.getDeleted().equals(false) && currentCattleExpense.getCattle().getId().equals(anCattle.getId()))
                    .collect(Collectors.toList());
    }

    public CattleExpense create(CattleExpense anCattleExpense) throws EntityAlreadyExistsException, AnyPersistenceException {

        try {
            return cattleExpenseRepository.save(anCattleExpense);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public CattleExpense update(CattleExpense entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		
		try {
            return cattleExpenseRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }
}
