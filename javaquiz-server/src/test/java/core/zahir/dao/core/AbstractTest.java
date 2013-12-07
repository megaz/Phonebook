package core.zahir.dao.core;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.naming.java.javaURLContextFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.naming.Context;
import javax.naming.InitialContext;

/***
 *  Abstract Test that sets up creating Derby Database for Persistence tests
 *  
 *  @see From http://www.alexecollins.com/content/tutorial-hibernate-jpa-part-1/
 *  
 * ***/
public abstract class AbstractTest {
	
	private static final String DATABASE_PATH = "java:comp/env/jdbc/JavaQuizDB";

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, javaURLContextFactory.class.getName());
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
		InitialContext ic = new InitialContext();

		ic.createSubcontext("java:");
		ic.createSubcontext("java:comp");
		ic.createSubcontext("java:comp/env");
		ic.createSubcontext("java:comp/env/jdbc");

		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDatabaseName("JavaQuizDB");
		// tell Derby to create the database if it does not already exist
		ds.setCreateDatabase("create");
 
		ic.bind(DATABASE_PATH, ds);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {

		InitialContext ic = new InitialContext();

		try {
			ic.unbind(DATABASE_PATH);
		}
		catch(Exception e) {}
	}
}