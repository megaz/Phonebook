package com.zahir.controller.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.zahir.entity.Contact;
import com.zahir.service.PhonebookService;

/***
 * Phonebook Reource that handles requests for the phonebook service to 
 * return and persist behind the scenes. 
 *  @author Zahir 
 ***/
@Path("phonebook")
public class PhonebookResource {

	public static final Logger LOG = Logger.getLogger(PhonebookResource.class);
	@Inject private PhonebookService phonebookService;

	/*
	 * Gets all contacts from the database
	 */
	@Path("/contacts")
	@GET
	public Response getAllContacts() {

		List<Contact> contacts = phonebookService.getAllContacts();

		return Response.ok(new Gson().toJson(contacts),
				MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Gets contact from the database that matches name and phoneNumber
	 * @param name - Name of contact
	 * @param phoneNumber - phoneNumber of contact
	 **/
	@Path("/search")
	@GET
	public Response searchByNameAndNumber(@QueryParam("name") String name,
			@QueryParam("phoneNumber") String phoneNumber) {

		if (StringUtils.isBlank(name) && StringUtils.isBlank(phoneNumber)) {
			List<Contact> contacts = phonebookService.getAllContacts();
			return Response.ok(new Gson().toJson(contacts),
					MediaType.APPLICATION_JSON).build();
		} else {
			Contact contact = phonebookService.searchPhonebook(name, phoneNumber);
			if(contact != null) {
				return Response.ok(new Gson().toJson(contact),
						MediaType.APPLICATION_JSON).build();
			}
			return Response.noContent().build();
		}
	}

	/**
	 * Gets all contacts from the database that contains name
	 * @param name - Name of contact 
	 * 
	 **/
	@Path("/search/name/{name}")
	@GET
	public Response searchByName(@PathParam("name") String name) {
		List<Contact> contacts = phonebookService.searchPhonebook(name);

		return Response.ok(new Gson().toJson(contacts),
				MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Adds contact to the database
	 * @param json - Contact json
	 **/
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContact(ContactJSON json) throws Exception {
		Contact contact = new Contact();
		contact.setName(json.getName());
		contact.setPhoneNumber(json.getPhoneNumber());
		try {
			phonebookService.save(contact);
		} catch (Exception e) {
			LOG.error("Error persisting contact");
			return Response.status(Response.Status.CONFLICT).entity("Contact Already Exists").build();
		}
		return Response.status(Response.Status.CREATED).entity("Created Contact").build();
	}

}
