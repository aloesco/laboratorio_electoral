package es.upm.dit.isst.LabElec.dao;

import java.util.List;
import es.upm.dit.isst.LabElec.dao.model.Usuario;

public interface UsuarioDAO {
	
	                
	public void createUser( Usuario user );

	public Usuario readUser( String email );

	
	public Usuario loginUser( String email, String password );
    


}
