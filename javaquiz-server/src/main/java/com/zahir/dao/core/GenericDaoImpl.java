package com.zahir.dao.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import core.zahir.exceptions.EntitiyException;


public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em = DAOHelper.getInstance().getEntityManager();
    protected EntityTransaction tx = em.getTransaction();
    
    protected static final String HIBERNATE_COMMENT_HINT_TYPE = "org.hibernate.comment";

    private Class<T> type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
    
    
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }
    
    public void setEntityTransaction(EntityTransaction tx) {
    	this.tx = tx;
    }

    @Override
    public long countAll(final Map<String, Object> params) {
    	return 0;
    }

	private String getPersistentClassName() {
		return type.getName();
	}
    
	@Override
	public T persist(final T t) {
		tx.begin();
		try {
			this.em.persist(t);
		}
		catch (Exception e) {
			tx.rollback();
			throw new EntitiyException("Could not persist enitiy", e);
		}
		
		tx.commit();
		return t;
	}

    @Override
    public void delete(final Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    @Override
    public T find(final Object id) {
        return (T) this.em.find(type, id);
    }
    
	public void refresh(T entity) {
		em.refresh(entity);
	}
    
    public List<T> findAll() {
        return (List<T> ) this.em.createQuery(String.format("SELECT t FROM %s t",getPersistentClassName())).getResultList();
    }
    
	protected void setCacheable(Query query) {
		query.setHint("org.hibernate.cacheable", true);
	}
	
    @Override
    public T update(final T t) {
        return this.em.merge(t);    
    }
}