package org.tax.service;

import java.util.List;

public interface BaseService<T> {
	
	List<T> getAll();

	int save(T t);

	int update(T t);

	int delete(Integer id);
	
	int delete(String id);

	T findById(Integer id);
	
	T findById(String id);
}
