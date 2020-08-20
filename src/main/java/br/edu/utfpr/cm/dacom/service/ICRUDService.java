package br.edu.utfpr.cm.dacom.service;


import java.util.List;

import br.edu.utfpr.cm.dacom.exception.AnyPersistenceException;
import br.edu.utfpr.cm.dacom.exception.EntityAlreadyExistsException;

public interface ICRUDService<T> {

    public List<T> readAll();
    public T create(T entity) throws EntityAlreadyExistsException, AnyPersistenceException;
    public T readById(Long anId);
    public void update(T entity);
    public void delete(Long anId);
}
