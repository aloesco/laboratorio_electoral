package es.upm.dit.isst.LabElec.dao.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;

@Entity
public class Circunscripcion {

	@Id
	private String nombre;
	private int poblacion;
	private int poblacionVotante;
	private String CCAA;
	
	@OneToMany(mappedBy="circunscripcion", fetch = FetchType.EAGER)
	private List<ResultadosVotos> resultadosVotos;
	
	public Circunscripcion() {
		resultadosVotos = new ArrayList<>();
	}
		
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(int poblacion) {
		this.poblacion=poblacion;
	}
	public int getPoblacionVotante() {
		return poblacionVotante;
	}
	public void setPoblacionVotante(int poblacionVotante) {
		this.poblacionVotante=poblacionVotante;
	}
	public List<ResultadosVotos> getElecciones() {
		return resultadosVotos;
	}

	public void setElecciones(List<ResultadosVotos> resultadosVotos) {
		this.resultadosVotos = resultadosVotos;
	}

	public String getCCAA() {
		return CCAA;
	}

	public void setCCAA(String CCAA) {
		this.CCAA = CCAA;
	}
}
