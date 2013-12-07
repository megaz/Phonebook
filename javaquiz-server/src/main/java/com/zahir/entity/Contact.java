package com.zahir.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name = "phonebook") 
@NamedQueries({
    @NamedQuery(name="Contact.findByName", query="SELECT c FROM Contact c WHERE lower(c.name) LIKE :contactName"),
    @NamedQuery(name="Contact.findByNameAndNumber", query="SELECT c FROM Contact c WHERE lower(c.name) = :contactName AND c.phoneNumber = :contactPhoneNumber")
})
public class Contact {
    @Id 
    @GeneratedValue 
    private Long id;

    @Column(length = 52, unique = true)
  
    private String name;
    
    @Column(length = 32, unique = true)
    private String phoneNumber;


    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}