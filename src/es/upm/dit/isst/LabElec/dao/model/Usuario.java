package es.upm.dit.isst.LabElec.dao.model;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Usuario {
	@Id
	private String email;
	
	private String password;
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
}