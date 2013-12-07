package com.zahir.dao.core;

import java.util.List;

import javax.persistence.TypedQuery;

import com.zahir.entity.Contact;

public class ContactDAO extends GenericDaoImpl<Contact> {


	public List<Contact> searchPhonebook(String name) {
		em.joinTransaction();
		String nq = "Contact.findByName";
	    TypedQuery<Contact> query = em.createNamedQuery(nq,Contact.class);
	    query.setParameter("contactName", "%"+name+"%");
	    query.setHint(HIBERNATE_COMMENT_HINT_TYPE, nq);
	    return query.getResultList();
	}
	
	public Contact searchPhonebook(String name, String phoneNumber) {
		em.joinTransaction();
		String nq = "Contact.findByNameAndNumber";
	    TypedQuery<Contact> query = em.createNamedQuery(nq,Contact.class);
	    query.setParameter("contactName", name);
	    query.setParameter("contactPhoneNumber", phoneNumber);
	    query.setHint(HIBERNATE_COMMENT_HINT_TYPE, nq);
	    return query.getSingleResult();
	}
	
	@Override
    public List<Contact> findAll() {
        return (List<Contact> ) this.em.createQuery(String.format("SELECT t FROM %s t ORDER BY t.name",Contact.class.getName())).getResultList();
    }

}
