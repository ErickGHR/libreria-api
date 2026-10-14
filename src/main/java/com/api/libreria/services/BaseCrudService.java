package com.api.libreria.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.libreria.web.ResourceNotFoundException;

public abstract class BaseCrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;
    private final String resourceName;

    protected BaseCrudService(JpaRepository<T, ID> repository, String resourceName) {
        this.repository = repository;
        this.resourceName = resourceName;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName + " no encontrado: " + id));
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        findById(id);
        repository.deleteById(id);
    }
}
