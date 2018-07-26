package es.upm.dit.isst.LabElec.dao;

import org.hibernate.Session;

import es.upm.dit.isst.LabElec.dao.model.Asignacion;
import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;
import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;

public class AsignacionDAOImplementation implements AsignacionDAO {

	private static AsignacionDAOImplementation instance;
	private AsignacionDAOImplementation() {};
	public static AsignacionDAOImplementation getInstance() {
		if(null == instance) {
			instance = new AsignacionDAOImplementation();
		}
		return instance;
	}
	
	@Override
	public void createAsignacion(Asignacion asignacion) {
	
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(asignacion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
	}

	@Override
	public int readAsignacion(int anno, Circunscripcion circunscripcion) {
		Session session = SessionFactoryService.get().openSession();
		Asignacion asignacion = null;
		try {
			session.beginTransaction();
			asignacion = (Asignacion) session.createQuery("select a from Asignacion a where a.anno = :anno and a.circunscripcion = :circunscripcion")
					.setParameter("anno", anno)
					.setParameter("circunscripcion", circunscripcion)
					.getSingleResult();
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		return asignacion.getEscannos();
	}

	@Override
	public void updateAsignacion(Asignacion asignacion) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(asignacion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void deleteAsignacion(Asignacion asignacion) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(asignacion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

}
