package br.com.cattle_control.starter.service;

import java.util.List;

import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;

public interface ICRUDService<T> {
    
    public List<T> readAll();
    public T create(T entity) throws EntityAlreadyExistsException, AnyPersistenceException;
    public T readById(Long anId);
    public void update(T entity);
    public void delete(Long anId);
}