package dao;

import java.util.List;

import models.Estudiante;

public interface EstudianteDAO {
	public List<Estudiante> getAllEstudiantes();
	public Estudiante getEstudiante(String key);
	public Estudiante createEstudiante(Estudiante src);
	public Estudiante updateEstudiante(String key, Estudiante src);
	public boolean deleteEstudiante(Estudiante e);
}
