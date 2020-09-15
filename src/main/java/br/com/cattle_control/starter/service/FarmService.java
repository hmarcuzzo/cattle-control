package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.FarmRepository;

@Service
@RequiredArgsConstructor
public class FarmService implements ICRUDService<Farm> {
    private final FarmRepository farmRepository;

    public List<Farm> readAll() {
        return farmRepository
                            .findAll()
                            .stream()
                            .filter(currentFarm -> currentFarm.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> getRegisterNumbers(String query) {
        return farmRepository
                            .findAll()
                            .stream()
                            .filter(currentFarm -> currentFarm.getRegisterNumber().toLowerCase().contains(query.toLowerCase()) && currentFarm.getDeleted().equals(false))
                            .map(Farm::getRegisterNumber)
                            .collect(Collectors.toList());
    }

    public List<String> getNames(String query) {
        return farmRepository
                            .findAll()
                            .stream()
                            .filter(currentFarm -> currentFarm.getName().toLowerCase().contains(query.toLowerCase()) && currentFarm.getDeleted().equals(false))
                            .map(Farm::getName)
                            .collect(Collectors.toList());
    }

    public Farm readById(Integer anId) throws EntityNotFoundException {
        return farmRepository
        .findAll()
        .stream()
        .filter(currentFarm -> currentFarm.getDeleted().equals(false) && currentFarm.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public Farm create(Farm anFarm) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (farmRepository
        		.findAll()
                .stream()
                .filter(currentFarm -> currentFarm.getDeleted().equals(false))
        		.anyMatch(currentFarm -> currentFarm.getRegisterNumber().equals(anFarm.getRegisterNumber()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return farmRepository.save(anFarm);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public Farm update(Farm entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (farmRepository
        		.findAll()
                .stream()
                .filter(currentFarm -> currentFarm.getDeleted().equals(false) && !currentFarm.getId().equals(entity.getId()))
        		.anyMatch(currentFarm -> currentFarm.getRegisterNumber().equals(entity.getRegisterNumber()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return farmRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public Farm findByRegisterNumber(String registerNumber) {
        return farmRepository.findAll()
                .stream()
                .filter(currentFarm -> currentFarm.getRegisterNumber().equals(registerNumber) && currentFarm.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Fazenda com número de registro " + registerNumber + " não encontrada"));
    }

}