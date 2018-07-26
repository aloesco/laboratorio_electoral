package es.upm.dit.isst.LabElec.dao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ResultadosVotos {
	
	@Id
	private String Id;
	
	private int anno;
	private String partido;
	private int votosObtenidos;
	
	@ManyToOne
	private Circunscripcion circunscripcion;
	
	public ResultadosVotos() {
		
	}
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public Circunscripcion getCircunscripcion(){
		return circunscripcion;
	}
	public void setCircunscripcion(Circunscripcion circunscripcion) {
		this.circunscripcion = circunscripcion;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public int getVotosObtenidos(){
		return votosObtenidos;
	}
	public void setVotosObtenidos(int votosObtenidos) {
		this.votosObtenidos = votosObtenidos;
	}
	
	
}
