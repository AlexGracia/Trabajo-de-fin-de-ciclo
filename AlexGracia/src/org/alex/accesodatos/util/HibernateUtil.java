package org.alex.accesodatos.util;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Clase que facilita el trabajo con Hibernate
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session session;

	/**
	 * Crea la factoria de sesiones
	 */
	public static void buildSessionFactory() {

		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Abre una nueva sesion
	 */
	public static void openSession() {
		session = sessionFactory.openSession();
	}

	/**
	 * Devuelve la sesion actual
	 * 
	 * @return session
	 */
	public static Session getCurrentSession() {

		if (!session.isOpen())
			openSession();

		return session;
	}

	/**
	 * Crea y devuelve una query con la consulta pasada por parametro.
	 * 
	 * @param consulta
	 * @return Query
	 */
	public static Query getQuery(String consulta) {
		return getCurrentSession().createQuery(consulta);
	}

	/**
	 * guardar, actualizar, borrar.
	 * 
	 * @param opcion
	 * @param obj
	 * @return boolean
	 */
	public static boolean setData(String opcion, Object obj) {
		// Empieza una transaccion.
		Session sesion = getCurrentSession();
		sesion.beginTransaction();
		try {
			switch (opcion) {
			case "guardar":
				sesion.save(obj);
				break;
			case "actualizar":
				sesion.update(obj);
				break;
			case "borrar":
				sesion.delete(obj);
				break;
			default:
			}
			sesion.getTransaction().commit();
		} catch (HibernateException he) {
			return false;
		} finally {
			sesion.close();
		}
		return true;
	}

}
