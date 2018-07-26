package es.upm.dit.isst.LabElec.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import es.upm.dit.isst.LabElec.dao.UsuarioDAOImplementation;

import es.upm.dit.isst.LabElec.dao.model.Usuario;

@WebServlet("/Registrar2Servlet")
public class Registrar2Servlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		Usuario user = new Usuario();
		user.setEmail(email);
		
		user.setPassword(password);
	
		UsuarioDAOImplementation.getInstance().createUser(user);
		
		
		resp.sendRedirect(req.getContextPath() + "/LaboratorioElectoral.jsp");

		
	}
	
}
