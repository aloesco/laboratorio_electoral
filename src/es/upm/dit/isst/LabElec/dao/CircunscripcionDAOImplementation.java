package es.upm.dit.isst.LabElec.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;
import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;

public class CircunscripcionDAOImplementation implements CircunscripcionDAO{
	private static CircunscripcionDAOImplementation instance;
	private CircunscripcionDAOImplementation() {};
	
	
	public static CircunscripcionDAOImplementation getInstance() {
		if(null == instance) {
			instance = new CircunscripcionDAOImplementation();
		}
		return instance;
	}

	@Override
	public void createCircunscripcion(Circunscripcion circunscripcion) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(circunscripcion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public Circunscripcion readCircunscripcion(String nombre) {
		Session session = SessionFactoryService.get().openSession();
		Circunscripcion circunscripcion = null;
		try {
			session.beginTransaction();
			circunscripcion = session.get(Circunscripcion.class,nombre);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		return circunscripcion;
	}

	@Override
	public void updateCircunscripcion(Circunscripcion circunscripcion) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(circunscripcion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
	}

	@Override
	public void deleteCircunscripcion(Circunscripcion circunscripcion) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(circunscripcion);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public List<Circunscripcion> readAllCircunscripcion() {
		List<Circunscripcion> circunscripciones = new ArrayList<>();
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			circunscripciones.addAll(
					session.createQuery("from Circunscripcion").list()
					);
			session.getTransaction().commit();
		}catch (Exception e) {
			
		}finally {
			session.close();
		}
		return circunscripciones;
	}

}
