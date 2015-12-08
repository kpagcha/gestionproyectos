package models;

public class Estudiante {
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String tituloProyecto;
	private String tutor1;
	private String tutor2;
	private String estadoProyecto;
	private String fechaPresentacionProyecto;
	private Float calificacionProyecto;
	
	private static String[] atributosActualizables = { "tituloProyecto", "estadoProyecto", "fechaPresentacionProyecto", "calificacionProyecto" };
	private static String[] posiblesEstados = { "en desarrollo", "presentado" };
	
	public static String[] getAtributosActualizables() { return atributosActualizables; }
	
	public static String[] getPosiblesEstados() { return posiblesEstados; }
	
	public String toString() {
		String str = "Autor: " + nombre + " " + primerApellido;
		if (segundoApellido != null && !segundoApellido.isEmpty()) str += " " + segundoApellido;
		str += "; Título del proyecto: " + tituloProyecto + "; ";
		if (tutor2 != null && !tutor2.isEmpty()) str += "Tutores: " + tutor1 + " y " + tutor2;
		else str += "Tutor: " + tutor1;
		str += "; Proyecto " + estadoProyecto;
		if (proyectoPresentado()) str += " en el día " + fechaPresentacionProyecto + " con una calificación de " + calificacionProyecto;

		return str;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getTituloProyecto() {
		return tituloProyecto;
	}
	public void setTituloProyecto(String tituloProyecto) {
		this.tituloProyecto = tituloProyecto;
	}
	public String getTutor1() {
		return tutor1;
	}
	public void setTutor1(String tutor1) {
		this.tutor1 = tutor1;
	}
	public String getTutor2() {
		return tutor2;
	}
	public void setTutor2(String tutor2) {
		this.tutor2 = tutor2;
	}
	public String getEstadoProyecto() {
		return estadoProyecto;
	}
	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	public String getFechaPresentacionProyecto() {
		return fechaPresentacionProyecto;
	}
	public void setFechaPresentacionProyecto(String fechaPresentacionProyecto) {
		this.fechaPresentacionProyecto = fechaPresentacionProyecto;
	}
	public Float getCalificacionProyecto() {
		return calificacionProyecto;
	}
	public void setCalificacionProyecto(Float calificacionProyecto) {
		this.calificacionProyecto = calificacionProyecto;
	}
	
	public boolean proyectoPresentado() { return estadoProyecto.equals("presentado"); }
	public boolean proyectoEnDesarrollo() { return estadoProyecto.equals("en desarrollo"); }
}
