package es.upm.dit.isst.LabElec.calculadoras;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.isst.LabElec.dao.*;
import es.upm.dit.isst.LabElec.dao.model.*;

public class CalcLagueCCAA {
	int anno;
	List<Circunscripcion> circs = CircunscripcionDAOImplementation.getInstance().readAllCircunscripcion();
	
	public CalcLagueCCAA(int anno) {
		this.anno = anno;
	}
	public List<Partido> ccaa(String ccaa){
		List<Partido> res = new ArrayList<>();
		List<String> partidosCCAA = new ArrayList<>();
		List<Integer> votosCCAA = new ArrayList<>();
		List<Integer> escannosCCAA = new ArrayList<>();
		int escCCAA = 0;
		for(Circunscripcion circ : circs) {
			if(circ.getCCAA().equals(ccaa)) {
				escCCAA+= AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
				List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
				for(ResultadosVotos elec:results) {
					if(partidosCCAA.contains(elec.getPartido())) {
						int puesto =partidosCCAA.indexOf(elec.getPartido());
						votosCCAA.set(puesto, votosCCAA.get(puesto)+elec.getVotosObtenidos());
					}else {
						partidosCCAA.add(elec.getPartido());
						votosCCAA.add(elec.getVotosObtenidos());
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
			
			votosCCAA.set(maximoParcial, votosAux.get(maximoParcial)/(2*escannosCCAA.get(maximoParcial)+1));
			escannosCCAA.set(maximoParcial, escannosCCAA.get(maximoParcial)+1);
		}
		List<String> auxpartidos = new ArrayList<>();
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
		
		return res;
	}
	public List<Partido> calcular() {
		List<Partido> res = new ArrayList<>();
		List<String> auxpartidos = new ArrayList<>();
		List<String> comunidades = new ArrayList<>();
		for(Circunscripcion circ : circs) {
			if(!comunidades.contains(circ.getCCAA())) {
				comunidades.add(circ.getCCAA());
			}
		}
		
		for(String comunidad : comunidades) {
			List<String> partidosCCAA = new ArrayList<>();
			List<Integer> votosCCAA = new ArrayList<>();
			List<Integer> escannosCCAA = new ArrayList<>();
			int escCCAA = 0;
			
			for (Circunscripcion circ : circs) {
				List<ResultadosVotos>  results = ResultadosVotosDAOImplementation.getInstance().readResultado(anno, circ);
				int esc = AsignacionDAOImplementation.getInstance().readAsignacion(anno, circ);
				if(circ.getCCAA().equals(comunidad)) {
					escCCAA = escCCAA + esc;
					for(ResultadosVotos elec:results) {
						if(partidosCCAA.contains(elec.getPartido())) {
							int puesto =partidosCCAA.indexOf(elec.getPartido());
							votosCCAA.set(puesto, votosCCAA.get(puesto)+elec.getVotosObtenidos());
						}else {
							partidosCCAA.add(elec.getPartido());
							votosCCAA.add(elec.getVotosObtenidos());
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
				
				votosCCAA.set(maximoParcial, votosAux.get(maximoParcial)/(2*escannosCCAA.get(maximoParcial)+1));
				escannosCCAA.set(maximoParcial, escannosCCAA.get(maximoParcial)+1);
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
}
