package core.zahir.dao.core;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zahir.dao.core.ContactDAO;
import com.zahir.entity.Contact;

public class TestContactDAO extends AbstractTest {

	private ContactDAO contactDAO;
	private static EntityManager entityManager;
	
	//Test Data
	private static final String PERSIST_DATA_NAME = "zahir";
	private static final String PERSIST_DATA_PHONENUMBER = "123456789";
	private static final String PERSIST_NAME_ROBERT = "Robert Johnathan Smith";
	private static final String PERSIST_NAME_ROBERT_PHONE_NUMBER = "111222333";
	private static final String PERSIST_NAME_JOHN_DOE = "John Doe";
	private static final String PERSIST_NAME_JOHN_DOE_PHONE_NUMBER = "111222444";
	private static final String PERSIST_NAME_JONATHAN_JONES = "Johnathan Jones";
	private static final String PERSIST_NAME_JONATHAN_JONES_PHONE_NUMBER = "111222555";
	
	private static final int EXPECTED_SEARCH_RESULT = 3;

	@Before
	public void setUp() throws Exception {
		entityManager = Persistence
				.createEntityManagerFactory("javaQuizTestPU")
				.createEntityManager();
		contactDAO = new ContactDAO();
		contactDAO.setEntityManager(entityManager);
		contactDAO.setEntityTransaction(entityManager.getTransaction());
	}

	@Test
	public void testPersistNewUser() {
		
		persistContact(PERSIST_DATA_NAME, PERSIST_DATA_PHONENUMBER);
		List<Contact> contacts = contactDAO.findAll();
		Assert.assertEquals(1, contacts.size());
		Contact dbContact = contacts.get(0);
		Assert.assertEquals(PERSIST_DATA_NAME, dbContact.getName());
		Assert.assertEquals(PERSIST_DATA_PHONENUMBER, dbContact.getPhoneNumber());
		Assert.assertNotNull(dbContact.getId());
	}
	
	private void persistTestData() {
		persistContact(PERSIST_NAME_ROBERT, PERSIST_NAME_ROBERT_PHONE_NUMBER );
		persistContact(PERSIST_NAME_JOHN_DOE, PERSIST_NAME_JOHN_DOE_PHONE_NUMBER);
		persistContact(PERSIST_NAME_JONATHAN_JONES, PERSIST_NAME_JONATHAN_JONES_PHONE_NUMBER);
	}
	
	@Test
	public void testSearchingUserByNameAndPhoneNumber() {
		Contact contact = contactDAO.searchPhonebook(PERSIST_DATA_NAME, PERSIST_DATA_PHONENUMBER);
		Assert.assertEquals(PERSIST_DATA_NAME, contact.getName());
		Assert.assertEquals(PERSIST_DATA_PHONENUMBER, contact.getPhoneNumber());
		
	}
	@Test
	public void testSearchingUserByName() {
		persistTestData();
		
		List<Contact> contacts = contactDAO.searchPhonebook("john");
		Assert.assertEquals(contacts.size(), EXPECTED_SEARCH_RESULT);
		
		contacts = contactDAO.searchPhonebook("doe");
		Assert.assertEquals(contacts.size(), 1);
		Assert.assertEquals(contacts.get(0).getName(), PERSIST_NAME_JOHN_DOE);
		Assert.assertEquals(contacts.get(0).getPhoneNumber(), PERSIST_NAME_JOHN_DOE_PHONE_NUMBER);
	}
	
	@Test
	public void testSearchingUserByNameWithNoPersistedContents() {
		List<Contact> contacts = contactDAO.searchPhonebook("jo");
		Assert.assertEquals(contacts.size(), 0);
	}

	private void persistContact(String name, String phoneNumber) {
		Contact contact = new Contact();
		contact.setName(name);
		contact.setPhoneNumber(phoneNumber);
		try {
			contactDAO.persist(contact);
		}
		catch(Exception e) {
			Assert.fail("Should not throw exception when persisting new user");
		}
	}
	
	private static int deleteAll() {
		entityManager.getTransaction().begin();
		try {
			Query query = entityManager.createNativeQuery("DELETE FROM phonebook");
		 	int result = query.executeUpdate();
		 	entityManager.getTransaction().commit();
		 	return result;
		}
		catch(Exception e ) {
			entityManager.getTransaction().rollback();
			return 0;
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		deleteAll();
		AbstractTest.tearDownClass();
	}
}
