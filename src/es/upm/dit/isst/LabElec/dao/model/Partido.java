package es.upm.dit.isst.LabElec.dao.model;

public class Partido {
	public String nombre;
	public int votos;
	public int escannos;
	public Partido(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public boolean equals(Object partido) {
		if(this.nombre == ((Partido) partido).nombre) {
			return true;
		}
		return false;
	}
	
	public String getNombre( ) {
		return nombre;
	}
	
	public int getVotos() {
		return votos;
	}
	
	public int getEscannos() {
		return escannos;
	}
}
