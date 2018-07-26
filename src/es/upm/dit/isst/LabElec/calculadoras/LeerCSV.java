package es.upm.dit.isst.LabElec.calculadoras;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import es.upm.dit.isst.LabElec.dao.AsignacionDAOImplementation;
import es.upm.dit.isst.LabElec.dao.CircunscripcionDAOImplementation;
import es.upm.dit.isst.LabElec.dao.ResultadosVotosDAOImplementation;
import es.upm.dit.isst.LabElec.dao.model.Asignacion;
import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;
import es.upm.dit.isst.LabElec.dao.model.ResultadosVotos;

public class LeerCSV {

	public LeerCSV() {
		
	}
	static boolean subidasCircs =false;
	public void subir(int anno) throws FileNotFoundException{
		
		String [] epigrafes = new String[5];
		String [][] datosCircunscripcion =new String[52][5];
		String [] partidos = {};
		int [] escannos = new int[52];
		int [][] datosVotosObtenidos = {{}};
		int contadorLinea = 0;
		File f = new File("/home/isst/Descargas/ISST2u/ISST2u/src/es/upm/dit/isst/LabElec/calculadoras/CONGRESO"+Integer.toString(anno)+".csv");
		Scanner s;
		s = new Scanner(f);
		while(s.hasNextLine()){
			String line = s.nextLine();
			String []lineaSeparada= {};
			if(contadorLinea==0) {
				//line.trim();
				lineaSeparada =line.split(";");
				partidos = new String[lineaSeparada.length - 5];
				for(int i=0;i<lineaSeparada.length;i++) {
					if(i<=4) {
						epigrafes[i]= lineaSeparada[i];
					}else {
						partidos[i-5] = lineaSeparada[i];
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
					}else {
						datosVotosObtenidos[contadorLinea-1][i-5]=Integer.parseInt(lineaSeparada[i]);
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
		for(int i = 0; i < datosCircunscripcion.length; i++) {
			Circunscripcion circ = new Circunscripcion();
			circ.setCCAA(datosCircunscripcion[i][0]);
			circ.setNombre(datosCircunscripcion[i][2]);
			circ.setPoblacion(Integer.parseInt(datosCircunscripcion[i][3]));
			circ.setPoblacionVotante(Integer.parseInt(datosCircunscripcion[i][4]));
			if(!subidasCircs) {
				CircunscripcionDAOImplementation.getInstance().createCircunscripcion(circ);
			}	
			Asignacion asig = new Asignacion();
			asig.setId(anno*1000+i);
			asig.setAnno(anno);
			asig.setCircunscripcion(circ);
			asig.setEscannos(escannos[i]);
			AsignacionDAOImplementation.getInstance().createAsignacion(asig);
			for(int j = 0; j < partidos.length; j++) {
				if(j != 1) {
					ResultadosVotos elec = new ResultadosVotos();
					elec.setId(partidos[j] + anno + circ.getNombre());
					elec.setAnno(anno);
					elec.setCircunscripcion(circ);
					elec.setPartido(partidos[j]);
					elec.setVotosObtenidos(datosVotosObtenidos[i][j]);
					if(elec.getVotosObtenidos() > 0) {
						ResultadosVotosDAOImplementation.getInstance().createResultado(elec);
					}
				}	
			}
		}
		
	}
	
	
	public static void main (String [] args) throws FileNotFoundException{
		LeerCSV l = new LeerCSV();
		l.subir(2015);
		subidasCircs = true;
		l.subir(2016);
		//subidasCircs = true;
		l.subir(1982);
		

	}
	
}
