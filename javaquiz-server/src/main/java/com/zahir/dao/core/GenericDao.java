package com.zahir.dao.core;


import java.util.Map;

public interface GenericDao<T> {
	
    long countAll(Map<String, Object> params);

    T persist(T t);

    void delete(Object id);

    T find(Object id);

    T update(T t);   
}