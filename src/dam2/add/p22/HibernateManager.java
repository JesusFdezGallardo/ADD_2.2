package dam2.add.p22;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 * Proporciona un objeto de la clase SessionFactory para ser utilizado con Hibernate
 */
public class HibernateManager {
	
	private static SessionFactory sessionFactory;
	
	private static SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null)
			sessionFactory = configureSessionFactory();
		return sessionFactory;
	}

}
