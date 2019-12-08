package com.springboot.mvc.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.mvc.service.GenericServiceApi;

/**
 * Esta clase genérica implementa un CRUD
 * @author GABO
 *
 * @param <T> entidad 
 * @param <ID> id de la entidad
 */

@Service
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericServiceApi<T, ID>{

	@Override
	@Transactional
	public T save(T entity) {
		return getRepository().save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return getRepository().findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public T findById(ID id) {
		return getRepository().findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(ID id) {
		getRepository().deleteById(id);
	}
	
	public abstract JpaRepository<T, ID> getRepository();

}
