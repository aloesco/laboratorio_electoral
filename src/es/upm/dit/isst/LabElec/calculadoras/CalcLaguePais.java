package es.upm.dit.isst.LabElec.calculadoras;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.isst.LabElec.dao.*;
import es.upm.dit.isst.LabElec.dao.model.*;

public class CalcLaguePais {
	int anno;
	List<Circunscripcion> circs = CircunscripcionDAOImplementation.getInstance().readAllCircunscripcion();
	public CalcLaguePais(int anno) {
		this.anno = anno;
	}

	public List<Partido> calcular(){
		List<Partido> res = new ArrayList<>();
		List<String> partidos = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		List<Integer> votos = new ArrayList<>();
		List<Integer> escannos = new ArrayList<>();
		int escPAIS = 0;
		for(Circunscripcion circ : circs) {
			List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
			int esc=AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
			escPAIS = escPAIS + esc;
			for(ResultadosVotos elec : results) {
				if(partidos.contains(elec.getPartido())) {
					int puesto =partidos.indexOf(elec.getPartido());
					votos.set(puesto, votos.get(puesto)+elec.getVotosObtenidos());
				}else {
					partidos.add(elec.getPartido());
					votos.add(elec.getVotosObtenidos());
				}
				escannos.add(0);
			}
		}
		int maximoParcial = 0;
		List<Integer> votosAux = new ArrayList<>(votos);
		for(int i =0;i<escPAIS;i++) {
			for(int j =0;j<votos.size();j++){
				if(votosAux.get(j)/(2*escannos.get(j)+1)> votosAux.get(maximoParcial)/(2*escannos.get(maximoParcial)+1)) {
					maximoParcial = j;
				}
			}
			
			votos.set(maximoParcial, votosAux.get(maximoParcial)/(2*escannos.get(maximoParcial)+1));
			escannos.set(maximoParcial, escannos.get(maximoParcial)+1);
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
		return res;
	}


}