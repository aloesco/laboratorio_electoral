package es.upm.dit.isst.LabElec.servlets;



import java.io.File;
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
import es.upm.dit.isst.LabElec.calculadoras.CalcFile;
import es.upm.dit.isst.LabElec.calculadoras.CalcLagueCCAA;
import es.upm.dit.isst.LabElec.calculadoras.CalcLaguePais;
import es.upm.dit.isst.LabElec.calculadoras.CalcLagueProv;

import es.upm.dit.isst.LabElec.dao.model.Partido;


/**
 * Servlet implementation class Redireccionar
 */
@WebServlet("/RedireccionarServlet")
public class RedireccionarServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ley = req.getParameter("ley");
		String ano = req.getParameter("ano");
		String circu = req.getParameter("circu");
		String est = req.getParameter("est");
		List<Partido> list = new ArrayList<>();
		File f = (File) req.getSession().getAttribute("file");
		
//		CalcDhontProv calc = new CalcDhontProv(2016);
//		List<Partido> list = calc.calcular();
		if(f != null) {
			CalcFile c = new CalcFile();
			if(ley.contentEquals("D'Hont")) {
				if(circu.contentEquals("provincia")) {
					try {
						list = c.calcDhontProv(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(circu.contentEquals("comunidad")) {
					try {
						list = c.calcDhontCCAA(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(circu.contentEquals("pais")) {
					try {
						list = c.calcDhontPais(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(ley.contentEquals("Saint Lagüe")) {
				if(circu.contentEquals("provincia")) {
					try {
						list = c.calcLagueProv(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(circu.contentEquals("comunidad")) {
					try {
						list = c.calcLagueCCAA(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(circu.contentEquals("pais")) {
					try {
						list = c.calcLaguePais(f);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else {
			if(ley.contentEquals("D'Hont")) {
				if(ano.contentEquals("1982")) {
					if(circu.contentEquals("provincia")) {
						CalcDhontProv calc = new CalcDhontProv(1982);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcDhontCCAA calc = new CalcDhontCCAA(1982);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcDhontPais calc = new CalcDhontPais(1982);
						list = calc.calcular();
					}
				}
				if(ano.contentEquals("2016")) {
					if(circu.contentEquals("provincia")) {
						CalcDhontProv calc = new CalcDhontProv(2016);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcDhontCCAA calc = new CalcDhontCCAA(2016);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcDhontPais calc = new CalcDhontPais(2016);
						list = calc.calcular();
					}
				}
				if(ano.contentEquals("2015")) {
					if(circu.contentEquals("provincia")) {
						CalcDhontProv calc = new CalcDhontProv(2015);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcDhontCCAA calc = new CalcDhontCCAA(2015);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcDhontPais calc = new CalcDhontPais(2015);
						list = calc.calcular();
					}
				}

			}
			
			if(ley.contentEquals("Saint Lagüe")) {
				if(ano.contentEquals("1982")) {
					if(circu.contentEquals("provincia")) {
						CalcLagueProv calc = new CalcLagueProv(1982);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcLagueCCAA calc = new CalcLagueCCAA(1982);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcLaguePais calc = new CalcLaguePais(1982);
						list = calc.calcular();
					}
				}
				if(ano.contentEquals("2016")) {
					if(circu.contentEquals("provincia")) {
						CalcLagueProv calc = new CalcLagueProv(2016);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcLagueCCAA calc = new CalcLagueCCAA(2016);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcLaguePais calc = new CalcLaguePais(2016);
						list = calc.calcular();
					}
				}
				if(ano.contentEquals("2015")) {
					if(circu.contentEquals("provincia")) {
						CalcLagueProv calc = new CalcLagueProv(2015);
						list = calc.calcular();
						
					}
					if(circu.contentEquals("comunidad")) {
						CalcLagueCCAA calc = new CalcLagueCCAA(2015);
						list = calc.calcular();
					}
					if(circu.contentEquals("pais")) {
						CalcLaguePais calc = new CalcLaguePais(2015);
						list = calc.calcular();
					}
				}

			}
		}
		
		
		List<Partido> listEsc = new ArrayList<>();
		List<Partido> listVotos = new ArrayList<>();
		Comparator<Partido> escComparator = new Comparator() {
			@Override
			public int compare(Object p1, Object p2) {
				return new Integer(((Partido)p2).escannos).compareTo(new Integer(((Partido)p1).escannos));
			}
		};
		Comparator<Partido> votosComparator = new Comparator() {
			@Override
			public int compare(Object p1, Object p2) {
				return new Integer(((Partido)p2).votos).compareTo(new Integer(((Partido)p1).votos));
			}
		};
		Collections.sort(list, votosComparator);
		int cont = 0;
		Partido otros = new Partido("Otros");
		for(Partido p : list) {
			if(cont < 4) {
				listVotos.add(p);
			}else {
				otros.votos += p.votos;
			}
			cont++;
		}
		cont = 0;
		int x = -1;
		Collections.sort(list, escComparator);
		for(Partido p : list) {
			if(p.escannos == 0 && x < 0) {
				x = list.indexOf(p);
			}
			if(cont < 4) {
				listEsc.add(p);
			}else {
				otros.escannos += p.escannos;
			}
			cont++;
		}
		listEsc.add(otros);
		listVotos.add(otros);
		for(int i = list.size()-1; i >= x ; i--) {
			list.remove(i);
		}
		
		
		 String nombre1 = listVotos.get(0).nombre;
		 String nombre2 = listVotos.get(1).nombre;
		  String nombre3 = listVotos.get(2).nombre;
		  String nombre4 = listVotos.get(3).nombre;
		  String nombre5 = listVotos.get(4).nombre;
		
		
		
		
		  double partido1 = listVotos.get(0).votos;
		  double partido2 = listVotos.get(1).votos;
		  double partido3 = listVotos.get(2).votos;
		  double partido4 = listVotos.get(3).votos;
		  double otrospartidos = listVotos.get(4).votos;
		  
		  double partido1e = listVotos.get(0).escannos;
		  double partido2e = listVotos.get(1).escannos;
		  double partido3e = listVotos.get(2).escannos;
		  double partido4e = listVotos.get(3).escannos;
		  double otrospartidose = listVotos.get(4).escannos;

		  
		  double total = partido1+partido2+partido3+partido4+otrospartidos;
		  
		  double total2 = partido1e+partido2e+partido3e+partido4e+otrospartidose;

		  
		  double porcentaje1 = (partido1/total)*180;
		  double porcentaje2 = (partido2/total)*180;
		  double porcentaje3 = (partido3/total)*180;
		  double porcentaje4 = (partido4/total)*180;
		  double porcentajeotros = (otrospartidos/total)*180;
		  
		  double porcentaje1e = (partido1e/total2)*180;
		  double porcentaje2e = (partido2e/total2)*180;
		  double porcentaje3e = (partido3e/total2)*180;
		  double porcentaje4e = (partido4e/total2)*180;
		  double porcentajeotrose = (otrospartidose/total2)*180;

		  
		  
		  int porcentaje1p = (int) Math.round((partido1/total)*100);
		  int porcentaje2p = (int) Math.round((partido2/total)*100);
		  int porcentaje3p = (int)Math.round((partido3/total)*100);
		  int porcentaje4p = (int)Math.round((partido4/total)*100);
		  int porcentajeotrosp = 100-porcentaje1p-porcentaje2p-porcentaje3p-porcentaje4p;
		  
		  int porcentaje1ep = (int)Math.round((partido1e/total2)*100);
		  int porcentaje2ep = (int)Math.round((partido2e/total2)*100);
		  int porcentaje3ep = (int)Math.round((partido3e/total2)*100);
		  int porcentaje4ep = (int)Math.round((partido4e/total2)*100);
		  int porcentajeotrosep = 100-porcentaje1ep-porcentaje2ep-porcentaje3ep-porcentaje4ep;
		  
		  
		  
		  //Numero numero = NumeroDAOImplementation.getInstance().readNumero(a);
		  req.getSession().setAttribute("numero1", porcentaje1);		  
		  req.getSession().setAttribute("numero2", porcentaje2);	
		  req.getSession().setAttribute("numero3", porcentaje3);	
		  req.getSession().setAttribute("numero4", porcentaje4);		
		  req.getSession().setAttribute("numero5", porcentajeotros);

		
		  req.getSession().setAttribute("numero1e", porcentaje1e);		
		  req.getSession().setAttribute("numero2e", porcentaje2e);	
		  req.getSession().setAttribute("numero3e", porcentaje3e);	
		  req.getSession().setAttribute("numero4e", porcentaje4e);
		  req.getSession().setAttribute("numero5e", porcentajeotrose);
		  
		  
		  req.getSession().setAttribute("numero1p", porcentaje1p);		  
		  req.getSession().setAttribute("numero2p", porcentaje2p);	
		  req.getSession().setAttribute("numero3p", porcentaje3p);	
		  req.getSession().setAttribute("numero4p", porcentaje4p);		
		  req.getSession().setAttribute("numero5p", porcentajeotrosp);

		
		  req.getSession().setAttribute("numero1ep", porcentaje1ep);		
		  req.getSession().setAttribute("numero2ep", porcentaje2ep);	
		  req.getSession().setAttribute("numero3ep", porcentaje3ep);	
		  req.getSession().setAttribute("numero4ep", porcentaje4ep);
		  req.getSession().setAttribute("numero5ep", porcentajeotrosep);
		  
		  
			
		  req.getSession().setAttribute("nombre1", nombre1);		
		  req.getSession().setAttribute("nombre2", nombre2);	
		  req.getSession().setAttribute("nombre3", nombre3);	
		  req.getSession().setAttribute("nombre4", nombre4);		
		  req.getSession().setAttribute("nombre5", nombre5);
		  


		  
		  
		req.getSession().setAttribute("ano", ano);
		req.getSession().setAttribute("circu", circu);
		req.getSession().setAttribute("ley", ley);
		req.getSession().setAttribute("res", list);
		req.getSession().setAttribute("est", est);

		//req.getSession().setAttribute("resgraficos", listEsc);

		if(f != null) {

				resp.sendRedirect(req.getContextPath()+ "/ResultadosGenerales.jsp");
			
		}else {
			if(est.equals("mapacom")) {
				resp.sendRedirect(req.getContextPath()+ "/MapaComunidades.jsp");
			}else {
				resp.sendRedirect(req.getContextPath()+ "/ResultadosGenerales.jsp");
			}		
		}

	}
	}

//}
