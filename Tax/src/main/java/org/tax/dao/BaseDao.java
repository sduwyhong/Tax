package org.tax.dao;

import java.util.List;

public interface BaseDao<T> {

	int save(T t);
	
	int delete(String id);
	
	int delete(Integer id);
	
	int update(T t);
	
	T findById(String id);
	
	T findById(Integer id);
	
	List<T> getAll();
}
