package es.upm.dit.isst.LabElec.servlets;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.LabElec.calculadoras.CalcDhontCCAA;
import es.upm.dit.isst.LabElec.calculadoras.CalcDhontPais;
import es.upm.dit.isst.LabElec.calculadoras.CalcDhontProv;
import es.upm.dit.isst.LabElec.calculadoras.CalcLagueCCAA;
import es.upm.dit.isst.LabElec.calculadoras.CalcLaguePais;
import es.upm.dit.isst.LabElec.calculadoras.CalcLagueProv;
import es.upm.dit.isst.LabElec.dao.model.Partido;




@WebServlet("/ComunidadesServlet")
public class ComunidadesServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String comunidad = req.getParameter("comunidad");
		String ley = req.getParameter("ley");
		String ano = req.getParameter("ano");
		String circu = req.getParameter("circu");
		
		List<Partido> list = new ArrayList<>();

		
		
		if(ley.contentEquals("D'Hont")) {
			if(ano.contentEquals("2016")) {
				if(circu.contentEquals("provincia")) {
					CalcDhontProv calc = new CalcDhontProv(2016);
					list = calc.ccaa(comunidad);
					
				}
				if(circu.contentEquals("comunidad")) {
					CalcDhontCCAA calc = new CalcDhontCCAA(2016);
					list = calc.ccaa(comunidad);
				}

			}
			if(ano.contentEquals("2015")) {
				if(circu.contentEquals("provincia")) {
					CalcDhontProv calc = new CalcDhontProv(2015);
					list = calc.ccaa(comunidad);
					
				}
				if(circu.contentEquals("comunidad")) {
					CalcDhontCCAA calc = new CalcDhontCCAA(2015);
					list = calc.ccaa(comunidad);
				}

			}

		}
		
		if(ley.contentEquals("Saint Lagüe")) {
			if(ano.contentEquals("2016")) {
				if(circu.contentEquals("provincia")) {
					CalcLagueProv calc = new CalcLagueProv(2016);
					list = calc.ccaa(comunidad);
					
				}
				if(circu.contentEquals("comunidad")) {
					CalcLagueCCAA calc = new CalcLagueCCAA(2016);
					list = calc.ccaa(comunidad);
				}

			}
			if(ano.contentEquals("2015")) {
				if(circu.contentEquals("provincia")) {
					CalcLagueProv calc = new CalcLagueProv(2015);
					list = calc.ccaa(comunidad);
					
				}
				if(circu.contentEquals("comunidad")) {
					CalcLagueCCAA calc = new CalcLagueCCAA(2015);
					list = calc.ccaa(comunidad);
				}

			}

		}
		
		
		req.getSession().setAttribute("comunidad", comunidad);

		req.getSession().setAttribute("ano", ano);
		req.getSession().setAttribute("circu", circu);
		req.getSession().setAttribute("ley", ley);
		req.getSession().setAttribute("res", list);

		resp.sendRedirect(req.getContextPath()+ "/MapaComunidades.jsp");
		
	}
		
		
}
	
