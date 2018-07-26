package es.upm.dit.isst.LabElec.servlets;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import es.upm.dit.isst.LabElec.calculadoras.CalcFile;
import es.upm.dit.isst.LabElec.dao.model.Partido;

@WebServlet("/ProcesaDatosServlet")
@MultipartConfig
public class ProcesaDatosServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Part filePart = req.getPart("file");
		InputStream fileContent = filePart.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		for (int length = 0; (length = fileContent.read(buffer)) > 0;) output.write(buffer, 0, length);
		File f = new File("/home/isst/Descargas/datos.csv");
		FileOutputStream fOS = new FileOutputStream(f);
		try {
			if(!f.exists()) {
				f.createNewFile();
			}
			fOS.write(output.toByteArray());
		} finally {
			fOS.close();
		}
		
		
		req.getSession().setAttribute("file", f);


		resp.sendRedirect(req.getContextPath()+ "/LaboratorioElectoral.jsp");
	}

}
