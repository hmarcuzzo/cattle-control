package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Place;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.PlaceRepository;


@Service
@RequiredArgsConstructor
public class PlaceService implements ICRUDService<Place> {
    private final PlaceRepository placeRepository;

    public List<Place> readAll() {
        return placeRepository
                            .findAll()
                            .stream()
                            .filter(currentPlace -> currentPlace.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }


    public Place readById(Integer anId) throws EntityNotFoundException {
        return placeRepository
                            .findAll()
                            .stream()
                            .filter(currentPlace -> currentPlace.getDeleted().equals(false) && currentPlace.getId().equals(anId))
                            .findFirst()
                            .orElseThrow(EntityNotFoundException::new);
    }


    public Place create(Place anPlace) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (placeRepository
        		.findAll()
                .stream()
                .filter(currentPlace -> currentPlace.getDeleted().equals(false))
        		.anyMatch(currentPlace -> currentPlace.getCep().equals(anPlace.getCep()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return placeRepository.save(anPlace);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }


    public Place update(Place entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (placeRepository
        		.findAll()
                .stream()
                .filter(currentPlace -> currentPlace.getDeleted().equals(false) && !currentPlace.getId().equals(entity.getId()))
        		.anyMatch(currentPlace -> currentPlace.getCep().equals(entity.getCep()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return placeRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
    

    @Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }


    public Place findByCep(String cep) {
        return placeRepository.findAll()
                .stream()
                .filter(currentPlace -> currentPlace.getCep().equals(cep) && currentPlace.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Local com CEP " + cep + " não encontrado"));
    }
}
