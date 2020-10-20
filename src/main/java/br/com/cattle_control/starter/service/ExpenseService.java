package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

// import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Expense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ICRUDService<Expense> {
    private final ExpenseRepository expenseRepository;

    public List<Expense> readAll() {
        return expenseRepository
                            .findAll()
                            .stream()
                            .filter(currentExpense -> currentExpense.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<Integer> getIds(String query) {
        return expenseRepository
                            .findAll()
                            .stream()
                            .filter(currentExpense -> Integer.toString(currentExpense.getId()).toLowerCase().contains(query.toLowerCase()) && currentExpense.getDeleted().equals(false))
                            .map(Expense::getId)
                            .collect(Collectors.toList());
    }

    public Expense readById(Integer anId) throws EntityNotFoundException {
        return expenseRepository
                        .findAll()
                        .stream()
                        .filter(currentExpense -> currentExpense.getDeleted().equals(false) && currentExpense.getId().equals(anId))
                        .findFirst()
                        .orElseThrow(EntityNotFoundException::new);
    }

    public Expense create(Expense anExpense) throws EntityAlreadyExistsException, AnyPersistenceException {

        try {
            return expenseRepository.save(anExpense);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public Expense update(Expense entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		
		try {
            return expenseRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public List<Expense> findByName(String expenseName) {
        return expenseRepository.findAll()
                .stream()
                .filter(currentExpense -> currentExpense.getExpense_name().toLowerCase().equals(expenseName.toLowerCase()) && currentExpense.getDeleted().equals(false))
                .collect(Collectors.toList());
    }
}
