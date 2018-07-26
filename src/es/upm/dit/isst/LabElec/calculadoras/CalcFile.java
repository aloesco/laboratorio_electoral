package es.upm.dit.isst.LabElec.calculadoras;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.upm.dit.isst.LabElec.dao.model.Partido;

public class CalcFile {

	String [] epigrafes = new String [5];
	String [][] datosCircunscripcion =new String[52][5];
	String [] partidos = {};
	int [] escannos = new int[52];
	int [][] datosVotosObtenidos = {{}};
	int contadorLinea = 0;
	Scanner s;
	
	private void leerCSV(File f) throws FileNotFoundException {
		s = new Scanner(f);
		while(s.hasNextLine()){
			String line = s.nextLine();
			String []lineaSeparada= {};
			if(contadorLinea==0) {
				//line.trim();
				lineaSeparada =line.split(";");
				partidos = new String[lineaSeparada.length - 6];
				for(int i=0;i<lineaSeparada.length;i++) {
					if(i<=4) {
						epigrafes[i]= lineaSeparada[i];
					}else if(i == 5){
						partidos[i-5] = lineaSeparada[i];
					}else if (i != 6) {
						partidos[i-6] = lineaSeparada[i];
					}
				}
				datosVotosObtenidos = new int[52][partidos.length];
				contadorLinea++;
			}
			else if(contadorLinea>0 && contadorLinea<53){
				//line.trim();
				lineaSeparada = line.split(";");
				for(int i=0;i<lineaSeparada.length;i++) {
					if(i<=4) {
						datosCircunscripcion[contadorLinea-1][i]=lineaSeparada[i];
					}else if (i == 5){
						datosVotosObtenidos[contadorLinea-1][i-5]=Integer.parseInt(lineaSeparada[i]);
					}else if(i != 6) {
						datosVotosObtenidos[contadorLinea-1][i-6]=Integer.parseInt(lineaSeparada[i]);
					}
				}	
				contadorLinea++;
			}else {
				lineaSeparada = line.split(";");
				for(int i =0;i<lineaSeparada.length;i++) {
					escannos[i]= Integer.parseInt(lineaSeparada[i]);
				}
				contadorLinea++;
			}
		}
		s.close();
	}
	
	public List<Partido> calcDhontCCAA(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		List<String> comunidades = new ArrayList<>();
		for(int i = 0; i < escannos.length; i++) {
			if(!comunidades.contains(datosCircunscripcion[i][0])) {
				comunidades.add(datosCircunscripcion[i][0]);
			}
		}
		// Lo unico reseñable aqui es que hay que guardar primero todos los partidos, los votos y los escaños de toda la CCAA;
		// Por eso hago primero un array de comunidades y despues voy comprobando que la provincia pertenezca a esa comunidad
		// Despues vamos añadiendo como en la anterior los resultados a RES; Varían poco los resultados aunque se ve como PP y 
		//PSOE pierden fuerza en detrimento de partidos nacionales que se presentan en toda españa como Podemos y C's
		//He cambiado todo de nombre mas que nada por si hace falta en algun momento cargar tanto resultados de provincias como 
		//de comunidad a la vez.
		for(String comunidad : comunidades) {
			List<String> partidosCCAA = new ArrayList<>();
			List<Integer> votosCCAA = new ArrayList<>();
			List<Integer> escannosCCAA = new ArrayList<>();
			int escCCAA = 0;
			
			for (int i = 0; i < escannos.length; i++) {
				//List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
				//int esc = AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
				if(datosCircunscripcion[i][0].equals(comunidad)) {
					escCCAA += escannos[i];
					for(int j = 0; j < partidos.length; j++) {
						if(partidosCCAA.contains(partidos[j])) {
							int puesto =partidosCCAA.indexOf(partidos[j]);
							votosCCAA.set(puesto, votosCCAA.get(puesto)+datosVotosObtenidos[i][j]);
						}else {
							partidosCCAA.add(partidos[j]);
							votosCCAA.add(datosVotosObtenidos[i][j]);
						}
						escannosCCAA.add(0);
					}
				}
				
			}
			int maximoParcial = 0;
			List<Integer> votosAux = new ArrayList<>(votosCCAA);
			for(int i =0;i<escCCAA;i++) {
				for(int j =0;j<votosCCAA.size();j++){
					if(votosAux.get(j)/(escannosCCAA.get(j)+1)> votosAux.get(maximoParcial)/(escannosCCAA.get(maximoParcial)+1)) {
						maximoParcial = j;
					}
				}
				//esto cambia: se ponen primero los escaños y leugo se dividen los votos, que sino da problemas
				escannosCCAA.set(maximoParcial, escannosCCAA.get(maximoParcial)+1);
				votosCCAA.set(maximoParcial, votosAux.get(maximoParcial)/(escannosCCAA.get(maximoParcial)));
			}
			for(int i=0;i<votosCCAA.size();i++) {
				int var = -1;
				if(auxpartidos.contains(partidosCCAA.get(i))) {
					var = i;
					if(!partidosCCAA.get(var).equals(auxpartidos.get(var))){
						var = auxpartidos.indexOf(partidosCCAA.get(i));
					}	
					Partido p =res.get(var);
					p.votos += votosAux.get(i);
					p.escannos += escannosCCAA.get(i);
					res.set(var, p);
				}else {
					Partido p = new Partido(partidosCCAA.get(i));
					p.votos += votosAux.get(i);
					p.escannos += escannosCCAA.get(i);
					res.add(p);
					auxpartidos.add(partidosCCAA.get(i));
					
				}
			}
		}
		return res;
	}
	
	public List<Partido> calcDhontPais(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		int [] votosAux = new int [partidos.length];
		int [] escannosAux = new int [partidos.length];
		int escTot = 0;
		for(int i = 0; i < 52; i++) {
			escTot += escannos[i];
			for(int j = 0; j < partidos.length; j++) {
				votosAux[j] += datosVotosObtenidos[i][j];
			}	
		}
		int maximoParcial = 0;
		for(int i =0;i<escTot;i++) {
			for(int j =0;j<votosAux.length;j++){
				if(votosAux[j]/(escannosAux[j]+1)> votosAux[maximoParcial]/(escannosAux[maximoParcial]+1)) {
					maximoParcial = j;
				}
			}
			escannosAux[maximoParcial] += 1;
		}
		for(int i = 0; i < partidos.length; i++) {
			Partido p = new Partido(partidos[i]);
			p.votos = votosAux[i];
			p.escannos = escannosAux[i];
			res.add(p);
		}
		return res;
	}
	
	public List<Partido> calcDhontProv(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		for(int l = 0; l < 52; l++) {
			List<String> partidos_ = new ArrayList<>();
			List<Integer> votos_ = new ArrayList<>();
			List<Integer> escannos_ = new ArrayList<>();
			for(int j = 0; j < partidos.length; j++) {
				votos_.add(datosVotosObtenidos[l][j]);
				partidos_.add(partidos[j]);
				escannos_.add(0);
			}
			int maximoParcial = 0;
			List<Integer> votosAux = new ArrayList<>(votos_);
			for(int i =0;i<escannos[l];i++) {
				for(int j =0;j<votos_.size();j++){
					if(votosAux.get(j)/(escannos_.get(j)+1)> votosAux.get(maximoParcial)/(escannos_.get(maximoParcial)+1)) {
						maximoParcial = j;
					}
				}
				
				escannos_.set(maximoParcial, escannos_.get(maximoParcial)+1);
				votos_.set(maximoParcial, votosAux.get(maximoParcial)/(escannos_.get(maximoParcial)));
				
			}
			for(int i=0;i<votos_.size();i++) {
				int var = -1;
				if(auxpartidos.contains(partidos_.get(i))) {
					var = i;
					if(!partidos_.get(var).equals(auxpartidos.get(var))){
						var = auxpartidos.indexOf(partidos_.get(i));
					}	
					Partido p =res.get(var);
					p.votos += votosAux.get(i);
					p.escannos += escannos_.get(i);
					res.set(var, p);
				}else {
					Partido p = new Partido(partidos_.get(i));
					p.votos += votosAux.get(i);
					p.escannos += escannos_.get(i);
					res.add(p);
					auxpartidos.add(partidos_.get(i));
					
				}
			}
		}
		return res;
	}
	
	public List<Partido> calcLagueCCAA(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		List<String> comunidades = new ArrayList<>();
		for(int i = 0; i < escannos.length; i++) {
			if(!comunidades.contains(datosCircunscripcion[i][0])) {
				comunidades.add(datosCircunscripcion[i][0]);
			}
		}
		// Lo unico reseñable aqui es que hay que guardar primero todos los partidos, los votos y los escaños de toda la CCAA;
		// Por eso hago primero un array de comunidades y despues voy comprobando que la provincia pertenezca a esa comunidad
		// Despues vamos añadiendo como en la anterior los resultados a RES; Varían poco los resultados aunque se ve como PP y 
		//PSOE pierden fuerza en detrimento de partidos nacionales que se presentan en toda españa como Podemos y C's
		//He cambiado todo de nombre mas que nada por si hace falta en algun momento cargar tanto resultados de provincias como 
		//de comunidad a la vez.
		for(String comunidad : comunidades) {
			List<String> partidosCCAA = new ArrayList<>();
			List<Integer> votosCCAA = new ArrayList<>();
			List<Integer> escannosCCAA = new ArrayList<>();
			int escCCAA = 0;
			
			for (int i = 0; i < escannos.length; i++) {
				//List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
				//int esc = AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
				if(datosCircunscripcion[i][0].equals(comunidad)) {
					escCCAA += escannos[i];
					for(int j = 0; j < partidos.length; j++) {
						if(partidosCCAA.contains(partidos[j])) {
							int puesto =partidosCCAA.indexOf(partidos[j]);
							votosCCAA.set(puesto, votosCCAA.get(puesto)+datosVotosObtenidos[i][j]);
						}else {
							partidosCCAA.add(partidos[j]);
							votosCCAA.add(datosVotosObtenidos[i][j]);
						}
						escannosCCAA.add(0);
					}
				}
				
			}
			int maximoParcial = 0;
			List<Integer> votosAux = new ArrayList<>(votosCCAA);
			for(int i =0;i<escCCAA;i++) {
				for(int j =0;j<votosCCAA.size();j++){
					if(votosAux.get(j)/(2*escannosCCAA.get(j)+1)> votosAux.get(maximoParcial)/(2*escannosCCAA.get(maximoParcial)+1)) {
						maximoParcial = j;
					}
				}
				//esto cambia: se ponen primero los escaños y leugo se dividen los votos, que sino da problemas
				escannosCCAA.set(maximoParcial, escannosCCAA.get(maximoParcial)+1);
				votosCCAA.set(maximoParcial, votosAux.get(maximoParcial)/(escannosCCAA.get(maximoParcial)));
			}
			for(int i=0;i<votosCCAA.size();i++) {
				int var = -1;
				if(auxpartidos.contains(partidosCCAA.get(i))) {
					var = i;
					if(!partidosCCAA.get(var).equals(auxpartidos.get(var))){
						var = auxpartidos.indexOf(partidosCCAA.get(i));
					}	
					Partido p =res.get(var);
					p.votos += votosAux.get(i);
					p.escannos += escannosCCAA.get(i);
					res.set(var, p);
				}else {
					Partido p = new Partido(partidosCCAA.get(i));
					p.votos += votosAux.get(i);
					p.escannos += escannosCCAA.get(i);
					res.add(p);
					auxpartidos.add(partidosCCAA.get(i));
					
				}
			}
		}
		return res;
	}
	
	public List<Partido> calcLaguePais(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		int [] votosAux = new int [partidos.length];
		int [] escannosAux = new int [partidos.length];
		int escTot = 0;
		for(int i = 0; i < 52; i++) {
			escTot += escannos[i];
			for(int j = 0; j < partidos.length; j++) {
				votosAux[j] += datosVotosObtenidos[i][j];
			}	
		}
		int maximoParcial = 0;
		for(int i =0;i<escTot;i++) {
			for(int j =0;j<votosAux.length;j++){
				if(votosAux[j]/(2*escannosAux[j]+1)> votosAux[maximoParcial]/(2*escannosAux[maximoParcial]+1)) {
					maximoParcial = j;
				}
			}
			escannosAux[maximoParcial] += 1;
		}
		for(int i = 0; i < partidos.length; i++) {
			Partido p = new Partido(partidos[i]);
			p.votos = votosAux[i];
			p.escannos = escannosAux[i];
			res.add(p);
		}
		return res;
	}
	
	public List<Partido> calcLagueProv(File f) throws Exception {
		try {
			leerCSV(f);
		} catch (FileNotFoundException e) {
			throw new Exception("Error: Invalid file");
		}
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		for(int l = 0; l < 52; l++) {
			List<String> partidos_ = new ArrayList<>();
			List<Integer> votos_ = new ArrayList<>();
			List<Integer> escannos_ = new ArrayList<>();
			for(int j = 0; j < partidos.length; j++) {
				votos_.add(datosVotosObtenidos[l][j]);
				partidos_.add(partidos[j]);
				escannos_.add(0);
			}
			int maximoParcial = 0;
			List<Integer> votosAux = new ArrayList<>(votos_);
			for(int i =0;i<escannos[l];i++) {
				for(int j =0;j<votos_.size();j++){
					if(votosAux.get(j)/(2*escannos_.get(j)+1)> votosAux.get(maximoParcial)/(2*escannos_.get(maximoParcial)+1)) {
						maximoParcial = j;
					}
				}
				
				votos_.set(maximoParcial, votosAux.get(maximoParcial)/(2*escannos_.get(maximoParcial)+1));
				escannos_.set(maximoParcial, escannos_.get(maximoParcial)+1);
				
			}
			for(int i=0;i<votos_.size();i++) {
				int var = -1;
				if(auxpartidos.contains(partidos_.get(i))) {
					var = i;
					if(!partidos_.get(var).equals(auxpartidos.get(var))){
						var = auxpartidos.indexOf(partidos_.get(i));
					}	
					Partido p =res.get(var);
					p.votos += votosAux.get(i);
					p.escannos += escannos_.get(i);
					res.set(var, p);
				}else {
					Partido p = new Partido(partidos_.get(i));
					p.votos += votosAux.get(i);
					p.escannos += escannos_.get(i);
					res.add(p);
					auxpartidos.add(partidos_.get(i));
					
				}
			}
		}
		return res;
	}
}
