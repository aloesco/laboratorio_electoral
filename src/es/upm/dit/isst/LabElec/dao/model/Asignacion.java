package es.upm.dit.isst.LabElec.dao.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Asignacion {

	@Id
	private int Id;
	
	private int anno;
	private int escannos;
	@ManyToOne
	private Circunscripcion circunscripcion;
	
	public Asignacion() {
	}
		
	
	public int getId() {
		return Id;
	}
	public void setId(int Id) {
		this.Id = Id;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno=anno;
	}
	public int getEscannos() {
		return escannos;
	}
	public void setEscannos(int escannos) {
		this.escannos=escannos;
	}
	public Circunscripcion getCircunscripcion() {
		return circunscripcion;
	}

	public void setCircunscripcion(Circunscripcion circunscripcion) {
		this.circunscripcion = circunscripcion;
	}
}
