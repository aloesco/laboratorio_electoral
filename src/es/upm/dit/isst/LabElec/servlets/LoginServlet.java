package es.upm.dit.isst.LabElec.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.LabElec.dao.UsuarioDAOImplementation;
import es.upm.dit.isst.LabElec.dao.model.Usuario;



@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	
	private final String USER_ADMIN = "admin";
	private final String PASS_ADMIN = "admin";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		Usuario user = UsuarioDAOImplementation.getInstance().loginUser(email, password);
		
		if (USER_ADMIN.equals(email) && PASS_ADMIN.equals(password)){
			
			resp.sendRedirect(req.getContextPath() + "/ElegirDatos.jsp");
			
			
			
		}else if(null!=user){
			req.getSession().setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/ElegirDatos.jsp");
			
			
		
		}else {
			resp.sendRedirect(req.getContextPath() + "/LaboratorioElectoral.jsp");
		}
	}
	
}
