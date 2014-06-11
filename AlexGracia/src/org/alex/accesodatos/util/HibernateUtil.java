package org.alex.accesodatos.util;

import org.alex.libs.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Clase que facilita el trabajo con Hibernate
 * 
 * @author Santiago Faci and Alex Gracia
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
	 * Abre una nueva sesi—n
	 */
	public static void openSession() {
		session = sessionFactory.openSession();
	}

	/**
	 * Devuelve la sesi—n actual
	 * 
	 * @return
	 */
	public static Session getCurrentSession() {

		if (!session.isOpen())
			openSession();

		return session;
	}

	/**
	 * Cierra Hibernate
	 */
	public static void closeSessionFactory() {

		if (session != null)
			session.close();

		if (sessionFactory != null)
			sessionFactory.close();
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
	 * @param mensaje
	 */
	public static void setData(String opcion, Object obj, String mensaje) {
		Session sesion = beginTransaction();
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
		closeTransaction(sesion, mensaje);
	}

	/**
	 * Empieza una transaccion.
	 * 
	 * @return sesion
	 */
	public static Session beginTransaction() {
		Session sesion = getCurrentSession();
		sesion.beginTransaction();
		return sesion;
	}

	/**
	 * Cierra una transaccion.
	 * 
	 * @param sesion
	 * @param mensaje
	 */
	public static void closeTransaction(Session sesion, String mensaje) {
		try {
			sesion.getTransaction().commit();
		} catch (ConstraintViolationException cve) {
			Util.setMensajeError(mensaje);
		}
		sesion.close();
	}
}
