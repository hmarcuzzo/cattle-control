package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.Role;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService implements ICRUDService<Role> {
    private final RoleRepository roleRepository;

    public List<Role> readAll() {
        return roleRepository
                            .findAll()
                            .stream()
                            .filter(currentRole -> currentRole.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> readAllRoleName() {
        return roleRepository
                            .findAll()
                            .stream()
                            .filter(currentRole -> currentRole.getDeleted().equals(false))
                            .map(Role::getName)
                            .collect(Collectors.toList());
    }

    public Role readById(Integer anId) throws EntityNotFoundException {
        return roleRepository
        .findAll()
        .stream()
        .filter(currentRole -> currentRole.getDeleted().equals(false) && currentRole.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public List<String> getRoleNames(String query) {
        return roleRepository
                            .findAll()
                            .stream()
                            .filter(currentRole -> currentRole.getName().toLowerCase().contains(query.toLowerCase()) && currentRole.getDeleted().equals(false))
                            .map(Role::getName)
                            .collect(Collectors.toList());
    }

    public Role create(Role anRole) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (roleRepository
        		.findAll()
                .stream()
                .filter(currentRole -> currentRole.getDeleted().equals(false))
        		.anyMatch(currentRole -> currentRole.getName().toUpperCase().equals(anRole.getName().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return roleRepository.save(anRole);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public Role update(Role entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (roleRepository
        		.findAll()
                .stream()
                .filter(currentRole -> currentRole.getDeleted().equals(false) && !currentRole.getId().equals(entity.getId()))
        		.anyMatch(currentRole -> currentRole.getName().toUpperCase().equals(entity.getName().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return roleRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

    @Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findAll()
                .stream()
                .filter(currentRole -> currentRole.getName().toLowerCase().equals(roleName.toLowerCase()) && currentRole.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("O papel com o nome " + roleName + " não foi encontrado"));
    }
}
