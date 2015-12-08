package dao;

import java.util.List;

import db.EstudianteJDBC;
import models.Estudiante;
import validators.EstudianteValidator;

public class EstudianteDAOImpl implements EstudianteDAO {
	
	private EstudianteJDBC estudianteJDBC = new EstudianteJDBC();
	private EstudianteValidator estudianteValidator = null;
	
	public List<Estudiante> getAllEstudiantes() {
		List<Estudiante> estudiantes = estudianteJDBC.allEstudiantes();
		return estudiantes;
	}
	
	public Estudiante getEstudiante(String key) {
		if (key == null) {
			return null;
		}
		Estudiante estudiante = estudianteJDBC.getEstudiante(key);
		return estudiante;
	}
	
	public Estudiante createEstudiante(Estudiante src) {
		if (src == null) {
			return null;
		}
		estudianteValidator = new EstudianteValidator(src);
		if (!estudianteValidator.validate()) {
			return null;
		}
		Estudiante estudiante = estudianteJDBC.createEstudiante(src);
		return estudiante;
	}
	
	public Estudiante updateEstudiante(String key, Estudiante src) {
		if (key == null || src == null) {
			return null;
		}
		Estudiante e = estudianteJDBC.getEstudiante(key);
		if (e == null) {
			return null;
		}
		setEstudianteAttributes(src, e);
		estudianteValidator = new EstudianteValidator(src);
		if (!estudianteValidator.validate()) {
			return null;
		}
		Estudiante estudiante = estudianteJDBC.updateEstudiante(key, src);
		return estudiante;
	}
	
	public boolean deleteEstudiante(Estudiante e) {
		if (e == null) {
			return false;
		}
		return estudianteJDBC.deleteEstudiante(e);
	}
	
	private void setEstudianteAttributes(Estudiante target, Estudiante src) {
		String nombre = src.getNombre();
		String primerApellido = src.getPrimerApellido();
		String segundoApellido = src.getSegundoApellido();
		String tituloProyecto = src.getTituloProyecto();
		String tutor1 = src.getTutor1();
		String tutor2 = src.getTutor2();
		String estadoProyecto = src.getEstadoProyecto();
		String fechaPresentacionProyecto = src.getFechaPresentacionProyecto();
		Float calificacionProyecto = src.getCalificacionProyecto();
		
		String tNombre = target.getNombre();
		String tPrimerApellido = target.getPrimerApellido();
		String tSegundoApellido = target.getSegundoApellido();
		String tTitulo = target.getTituloProyecto();
 		String tTutor1 = target.getTutor1();
		String tTutor2 = target.getTutor2();
		String tEstado = target.getEstadoProyecto();
		String tFecha = target.getFechaPresentacionProyecto();
		
		if (tNombre == null || tNombre.isEmpty()) target.setNombre(nombre);
		if (tPrimerApellido == null || tPrimerApellido.isEmpty()) target.setPrimerApellido(primerApellido);
		if (tSegundoApellido == null || tSegundoApellido.isEmpty()) target.setSegundoApellido(segundoApellido);
		if (tTitulo == null || tTitulo.isEmpty()) target.setTituloProyecto(tituloProyecto);
		if (tTutor1 == null || tTutor1.isEmpty()) target.setTutor1(tutor1);
		if (tTutor2 == null || tTutor2.isEmpty()) target.setTutor2(tutor2);
		if (tEstado == null || tEstado.isEmpty()) target.setEstadoProyecto(estadoProyecto);
		if (tFecha == null || tFecha.isEmpty()) target.setFechaPresentacionProyecto(fechaPresentacionProyecto);
		if (target.getCalificacionProyecto() == null) target.setCalificacionProyecto(calificacionProyecto);
		
		if (target.getEstadoProyecto().equals("en desarrollo")) {
			target.setFechaPresentacionProyecto("");
			target.setCalificacionProyecto(null);
		}
	}
}
