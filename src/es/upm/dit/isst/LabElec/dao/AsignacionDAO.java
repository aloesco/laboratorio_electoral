package es.upm.dit.isst.LabElec.dao;

import java.util.List;

import es.upm.dit.isst.LabElec.dao.model.Asignacion;
import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;

public interface AsignacionDAO {

	public void createAsignacion(Asignacion asignacion);

	public int readAsignacion(int anno, Circunscripcion circunscripcion);

	public void updateAsignacion(Asignacion asignacion);

	public void deleteAsignacion(Asignacion asignacion);

}
