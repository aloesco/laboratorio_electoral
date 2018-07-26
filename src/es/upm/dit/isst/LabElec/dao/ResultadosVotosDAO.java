package es.upm.dit.isst.LabElec.dao;

import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;
import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;
import java.util.List;


public interface ResultadosVotosDAO {
	
	public void createResultado(ResultadosVotos resultado);

	public List<ResultadosVotos> readResultado(int anno, Circunscripcion circunscripcion);
	
	public List<ResultadosVotos> readResultadosCCAA(int anno, String CCAA);
	
	public List<ResultadosVotos> readAllResultados(int anno);

	public void updateResultado(ResultadosVotos resultado);

	public void deleteResultado(ResultadosVotos resultado);

	public List<ResultadosVotos> readAllResultados();

}
