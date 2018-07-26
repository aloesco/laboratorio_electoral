package es.upm.dit.isst.LabElec.calculadoras;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.isst.LabElec.dao.*;
import es.upm.dit.isst.LabElec.dao.model.*;

public class CalcDhontProv {

	int anno;
	List<Circunscripcion> circs = CircunscripcionDAOImplementation.getInstance().readAllCircunscripcion();
	
	public CalcDhontProv(int anno) {
		this.anno = anno;
	}
	public List<Partido> ccaa(String ccaa){
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		for(Circunscripcion circ : circs) {
			if(circ.getCCAA().equals(ccaa)) {
				List<String> partidos = new ArrayList<>();
				List<Integer> votos = new ArrayList<>();
				List<Integer> escannos = new ArrayList<>();
				int esc = AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
				List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);

				for(ResultadosVotos elec: results) {
					votos.add(elec.getVotosObtenidos());
					partidos.add(elec.getPartido());
					escannos.add(0);
				}
				int maximoParcial = 0;
				List<Integer> votosAux = new ArrayList<>(votos);
				for(int i =0;i<esc;i++) {
					for(int j =0;j<votos.size();j++){
						if(votosAux.get(j)/(escannos.get(j)+1)> votosAux.get(maximoParcial)/(escannos.get(maximoParcial)+1)) {
							maximoParcial = j;
						}
					}

					escannos.set(maximoParcial, escannos.get(maximoParcial)+1);
					votos.set(maximoParcial, votosAux.get(maximoParcial)/(escannos.get(maximoParcial)));

				}
				for(int i=0;i<votos.size();i++) {
					int var = -1;
					if(auxpartidos.contains(partidos.get(i))) {
						var = i;
						if(!partidos.get(var).equals(auxpartidos.get(var))){
							var = auxpartidos.indexOf(partidos.get(i));
						}	
						Partido p =res.get(var);
						p.votos += votosAux.get(i);
						p.escannos += escannos.get(i);
						res.set(var, p);
					}else {
						Partido p = new Partido(partidos.get(i));
						p.votos += votosAux.get(i);
						p.escannos += escannos.get(i);
						res.add(p);
						auxpartidos.add(partidos.get(i));

					}
				}
			}	
		}
		
		return res;
	}
	public List<Partido> calcular() {
		
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		for(Circunscripcion circ : circs) {
			List<String> partidos = new ArrayList<>();
			List<Integer> votos = new ArrayList<>();
			List<Integer> escannos = new ArrayList<>();
			int esc = AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
			List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
			
			for(ResultadosVotos elec: results) {
				votos.add(elec.getVotosObtenidos());
				partidos.add(elec.getPartido());
				escannos.add(0);
			}
			int maximoParcial = 0;
			List<Integer> votosAux = new ArrayList<>(votos);
			for(int i =0;i<esc;i++) {
				for(int j =0;j<votos.size();j++){
					if(votosAux.get(j)/(escannos.get(j)+1)> votosAux.get(maximoParcial)/(escannos.get(maximoParcial)+1)) {
						maximoParcial = j;
					}
				}
				
				escannos.set(maximoParcial, escannos.get(maximoParcial)+1);
				votos.set(maximoParcial, votosAux.get(maximoParcial)/(escannos.get(maximoParcial)));
				
			}
			for(int i=0;i<votos.size();i++) {
				int var = -1;
				if(auxpartidos.contains(partidos.get(i))) {
					var = i;
					if(!partidos.get(var).equals(auxpartidos.get(var))){
						var = auxpartidos.indexOf(partidos.get(i));
					}	
					Partido p =res.get(var);
					p.votos += votosAux.get(i);
					p.escannos += escannos.get(i);
					res.set(var, p);
				}else {
					Partido p = new Partido(partidos.get(i));
					p.votos += votosAux.get(i);
					p.escannos += escannos.get(i);
					res.add(p);
					auxpartidos.add(partidos.get(i));
					
				}
			}
		}
		return res;
	}
}