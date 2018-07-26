package es.upm.dit.isst.LabElec.servlets;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BorraFicheroServlet")
@MultipartConfig
public class BorraFicheroServlet  extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getSession().setAttribute("file", null);

	
		
		resp.sendRedirect(req.getContextPath() + "/LaboratorioElectoral.jsp");

	}
	
	
}
