package es.upm.dit.isst.LabElec.dao;

import java.util.List;

import es.upm.dit.isst.LabElec.dao.model.Circunscripcion;

public interface CircunscripcionDAO {
		

	public void createCircunscripcion( Circunscripcion circunscripcion );

	public Circunscripcion readCircunscripcion( String nombre );

	public void updateCircunscripcion( Circunscripcion circunscripcion );

	public void deleteCircunscripcion( Circunscripcion circunscripcion );

	public List<Circunscripcion> readAllCircunscripcion();



}
