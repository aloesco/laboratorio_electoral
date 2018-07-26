package es.upm.dit.isst.LabElec.dao;



import org.hibernate.Session;

import es.upm.dit.isst.LabElec.dao.model.Usuario;

public class UsuarioDAOImplementation implements UsuarioDAO{

	private static UsuarioDAOImplementation instance;
	private UsuarioDAOImplementation() {};
	public static UsuarioDAOImplementation getInstance() {
		if(null == instance) {
			instance = new UsuarioDAOImplementation();
		}
		return instance;
	};

	@Override
	public void createUser(Usuario user) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}

	}

	@Override
	public Usuario readUser(String email) {
		Usuario user = null;
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			user = session.get(Usuario.class, email);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		return user;
	}



	@Override
	public Usuario loginUser(String email, String password) {
		Session session = SessionFactoryService.get().openSession();
		Usuario user = null;
		try {
			session.beginTransaction();
			user = (Usuario) session.createQuery("select u from Usuario u where u.email = :email and u.password = :password")
					.setParameter("email", email)
					.setParameter("password", password)
					.getSingleResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		return user;
	}
}