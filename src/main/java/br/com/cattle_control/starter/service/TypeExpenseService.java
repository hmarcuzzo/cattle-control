package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.TypeExpense;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.TypeExpenseRepository;


@Service
@RequiredArgsConstructor
public class TypeExpenseService implements ICRUDService<TypeExpense> {
    private final TypeExpenseRepository typeExpenseRepository;

    public List<TypeExpense> readAll() {
        return typeExpenseRepository
                            .findAll()
                            .stream()
                            .filter(currentTypeExpense -> currentTypeExpense.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> readAllTypeExpenseName() {
        return typeExpenseRepository
                            .findAll()
                            .stream()
                            .filter(currentTypeExpense -> currentTypeExpense.getDeleted().equals(false))
                            .map(TypeExpense::getTypeExpense_name)
                            .collect(Collectors.toList());
    }

    public TypeExpense readById(Integer anId) throws EntityNotFoundException {
        return typeExpenseRepository
        .findAll()
        .stream()
        .filter(currentTypeExpense -> currentTypeExpense.getDeleted().equals(false) && currentTypeExpense.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public List<String> getTypeExpenseNames(String query) {
        return typeExpenseRepository
                            .findAll()
                            .stream()
                            .filter(currentTypeExpense -> currentTypeExpense.getTypeExpense_name().toLowerCase().contains(query.toLowerCase()) && currentTypeExpense.getDeleted().equals(false))
                            .map(TypeExpense::getTypeExpense_name)
                            .collect(Collectors.toList());
    }

    public TypeExpense create(TypeExpense anTypeExpense) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (typeExpenseRepository
        		.findAll()
                .stream()
                .filter(currentTypeExpense -> currentTypeExpense.getDeleted().equals(false))
        		.anyMatch(currentTypeExpense -> currentTypeExpense.getTypeExpense_name().toUpperCase().equals(anTypeExpense.getTypeExpense_name().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return typeExpenseRepository.save(anTypeExpense);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public TypeExpense update(TypeExpense entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (typeExpenseRepository
        		.findAll()
                .stream()
                .filter(currentTypeExpense -> currentTypeExpense.getDeleted().equals(false) && !currentTypeExpense.getId().equals(entity.getId()))
        		.anyMatch(currentTypeExpense -> currentTypeExpense.getTypeExpense_name().toUpperCase().equals(entity.getTypeExpense_name().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return typeExpenseRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

    @Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public TypeExpense findByTypeExpenseName(String typeExpenseName) {
        return typeExpenseRepository.findAll()
                .stream()
                .filter(currentTypeExpense -> currentTypeExpense.getTypeExpense_name().toLowerCase().equals(typeExpenseName.toLowerCase()) && currentTypeExpense.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Tipo de gasto com o nome " + typeExpenseName + " não foi encontrado"));
    }
}
