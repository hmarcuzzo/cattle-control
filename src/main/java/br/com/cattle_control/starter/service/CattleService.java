package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.CattleRepository;

@Service
@RequiredArgsConstructor
public class CattleService implements ICRUDService<Cattle> {
    private final CattleRepository cattleRepository;

    public List<Cattle> readAll() {
        return cattleRepository
                            .findAll()
                            .stream()
                            .filter(currentCattle -> currentCattle.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> getNumberings(String query) {
        return cattleRepository
                            .findAll()
                            .stream()
                            .filter(currentCattle -> (currentCattle.getNumbering()).toLowerCase().contains(query.toLowerCase()) && currentCattle.getDeleted().equals(false))
                            .map(Cattle::getNumbering)
                            .collect(Collectors.toList());
    }


    public Cattle readById(Integer anId) throws EntityNotFoundException {
        return cattleRepository
        .findAll()
        .stream()
        .filter(currentCattle -> currentCattle.getDeleted().equals(false) && currentCattle.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public Cattle create(Cattle anCattle) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (cattleRepository
        		.findAll()
                .stream()
                .filter(currentCattle -> currentCattle.getDeleted().equals(false))
        		.anyMatch(currentCattle -> currentCattle.getNumbering().equals(anCattle.getNumbering()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return cattleRepository.save(anCattle);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


	public Cattle update(Cattle entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (cattleRepository
        		.findAll()
                .stream()
                .filter(currentCattle -> currentCattle.getDeleted().equals(false) && !currentCattle.getId().equals(entity.getId()))
        		.anyMatch(currentCattle -> currentCattle.getNumbering().equals(entity.getNumbering()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return cattleRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public Cattle findByNumbering(String numbering) {
        return cattleRepository.findAll()
                .stream()
                .filter(currentCattle -> currentCattle.getNumbering().equals(numbering) && currentCattle.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Boi com numeração " + numbering + " não encontrado"));
    }

}