package es.upm.dit.isst.LabElec.dao;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;
import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;
import org.hibernate.Session;

public class ResultadosVotosDAOImplementation implements ResultadosVotosDAO {

	private static ResultadosVotosDAOImplementation instance;
	private ResultadosVotosDAOImplementation() {};
	public static ResultadosVotosDAOImplementation getInstance() {
		if(null == instance) {
			instance = new ResultadosVotosDAOImplementation();
		}
		return instance;
	}
	
	@Override
	public void createResultado(ResultadosVotos resultado) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(resultado);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public List<ResultadosVotos> readResultado(int anno, Circunscripcion circunscripcion) {
		Session session = SessionFactoryService.get().openSession();
		List<ResultadosVotos> resultadosVotos = new ArrayList<>();
		try {
			session.beginTransaction();
			resultadosVotos.addAll( session.createQuery("select e from ResultadosVotos e where e.anno = :anno and e.circunscripcion = :circunscripcion")
					.setParameter("anno", anno)
					.setParameter("circunscripcion", circunscripcion).list());
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		return resultadosVotos;
	}

	@Override
	public List<ResultadosVotos> readResultadosCCAA(int anno, String CCAA) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResultadosVotos> readAllResultados(int anno) {
		List<ResultadosVotos> resultadosVotos = new ArrayList<>();
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			resultadosVotos.addAll(
					session.createQuery("select e from ResultadosVotos e where e.anno = :anno").setParameter("anno",anno).list()
					);
			session.getTransaction().commit();
		}catch (Exception e) {
			
		}finally {
			session.close();
		}
		return resultadosVotos;
	}

	@Override
	public void updateResultado(ResultadosVotos resultado) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(resultado);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void deleteResultado(ResultadosVotos resultado) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(resultado);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public List<ResultadosVotos> readAllResultados() {
		List<ResultadosVotos> resultadosVotos = new ArrayList<>();
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			resultadosVotos.addAll(
					session.createQuery("from ResultadosVotos").list()
					);
			session.getTransaction().commit();
		}catch (Exception e) {
			
		}finally {
			session.close();
		}
		return resultadosVotos;
	}

}
