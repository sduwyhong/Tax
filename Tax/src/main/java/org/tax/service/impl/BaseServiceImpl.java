package org.tax.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tax.dao.BaseDao;
import org.tax.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Setter
	private BaseDao baseDao;
	
	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public int save(T t) {
		return baseDao.save(t);
	}

	@Override
	public int update(T t) {
		return baseDao.update(t);
	}

	@Override
	public int delete(Integer id) {
		return baseDao.delete(id);
	}

	@Override
	public int delete(String id) {
		return baseDao.delete(id);
	}

	@Override
	public T findById(Integer id) {
		return (T) baseDao.findById(id);
	}

	@Override
	public T findById(String id) {
		return (T) baseDao.findById(id);
	}
	
	
}
