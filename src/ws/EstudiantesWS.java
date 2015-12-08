package ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dao.EstudianteDAO;
import dao.EstudianteDAOImpl;

import models.Estudiante;

@Path("/estudiantes")
public class EstudiantesWS {
	
	EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
	
	@GET
	@Produces({"application/json"})
	public List<Estudiante> getAllEstudiantes() {
		List<Estudiante> estudiantes = estudianteDAO.getAllEstudiantes();
		return estudiantes;
	}
	
	@GET
	@Path("/buscar/{estudiante}")
	@Produces({"text/plain"})
	public String getEstudiante(@PathParam("estudiante") String key) {
		Estudiante estudiante = estudianteDAO.getEstudiante(key);
		if (estudiante != null) {
			return estudiante.toString();
		} else {
			return null;
		}
	}
	
	@POST
	@Path("/crear")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Estudiante createEstudiante(Estudiante src) {
		Estudiante estudiante = estudianteDAO.createEstudiante(src);
		return estudiante;
	}
	
	@POST
	@Path("/formulario")
	@Consumes({"application/x-www-form-urlencoded"})
	@Produces({"application/json"})
	public Estudiante createEstudianteWithForm(
			@FormParam("nombre") String nombre,
			@FormParam("primerApellido") String primerApellido,
			@FormParam("segundoApellido") String segundoApellido,
			@FormParam("tituloProyecto") String tituloProyecto,
			@FormParam("tutor1") String tutor1,
			@FormParam("tutor2") String tutor2,
			@FormParam("estadoProyecto") String estadoProyecto,
			@FormParam("fechaPresentacionProyecto") String fechaPresentacionProyecto,
			@FormParam("calificacionProyecto") Float calificacionProyecto) {
		
		Estudiante src = new Estudiante();
		src.setNombre(nombre);
		src.setPrimerApellido(primerApellido);
		src.setSegundoApellido(segundoApellido);
		src.setTituloProyecto(tituloProyecto);
		src.setTutor1(tutor1);
		src.setTutor2(tutor2);
		src.setEstadoProyecto(estadoProyecto);
		src.setFechaPresentacionProyecto(fechaPresentacionProyecto);
		src.setCalificacionProyecto(calificacionProyecto);
		Estudiante estudiante = estudianteDAO.createEstudiante(src);
		return estudiante;
	}
	
	@PUT
	@Path("/actualizar/{estudiante}")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Estudiante updateEstudiante(@PathParam("estudiante") String key, Estudiante src) {
		Estudiante estudiante = estudianteDAO.updateEstudiante(key, src);
		return estudiante;
	}
	
	@DELETE
	@Path("/eliminar/{estudiante}")
	public boolean deleteEstudiante(@PathParam("estudiante") String key) {
		Estudiante estudiante = estudianteDAO.getEstudiante(key);
		return estudianteDAO.deleteEstudiante(estudiante);
	}
	
	@GET
	@Path("/fecha")
	@Produces({"application/json"})
	public models.Fecha fechaActual() {
		models.Fecha fecha = new models.Fecha();
		return fecha;
	}
}
