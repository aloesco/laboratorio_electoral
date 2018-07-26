package es.upm.dit.isst.LabElec.servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.LabElec.dao.UsuarioDAOImplementation;
import es.upm.dit.isst.LabElec.dao.model.Usuario;

@WebServlet("/RegistrarseServlet")
public class RegistrarseServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect(req.getContextPath() + "/Registrarse.jsp");
	}
}
