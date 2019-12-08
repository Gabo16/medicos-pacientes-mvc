package com.springboot.mvc.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericServiceApi<T, ID extends Serializable> {
	
	T save(T entity);
	
	Page<T> findAll(Pageable pageable);
	
	List<T> findAll();
	
	T findById(ID id);
	
	void deleteById(ID id);

}
