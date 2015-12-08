package validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Estudiante;

public class EstudianteValidator {
	private Estudiante estudiante;
	
	public EstudianteValidator(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	
	public boolean validate() {
		return validateNombre() && validatePrimerApellido() && validateTituloProyecto() &&
				validateTutor1() && validateEstadoProyecto() && validateFechaPresentacionProyecto() &&
				validateCalificacionProyecto();
	}
	
	public boolean validateNombre() {
		return validateNotEmptyString("nombre", estudiante.getNombre());
	}
	
	public boolean validatePrimerApellido() {
		return validateNotEmptyString("primerApellido", estudiante.getPrimerApellido());
	}
	
	public boolean validateTituloProyecto() {
		return validateNotEmptyString("tituloProyecto", estudiante.getTituloProyecto());
	}
	
	public boolean validateTutor1() {
		return validateNotEmptyString("tutor1", estudiante.getTutor1());
	}
	
	public boolean validateEstadoProyecto() {
		if (!validateNotEmptyString("estadoProyecto", estudiante.getEstadoProyecto())) {
			return false;
		}
		String[] posiblesEstados = Estudiante.getPosiblesEstados();
		String posiblesEstadosString = "";
		for (String estado : posiblesEstados) {
			if (estudiante.getEstadoProyecto().equals(estado)) {
				return true;
			}
			posiblesEstadosString += estado + ", ";
		}
		posiblesEstadosString = posiblesEstadosString.substring(0, posiblesEstadosString.length() - 2);
		System.out.println("estadoProyecto inválido (posibles estados: " + posiblesEstadosString + ")");
		return false;
	}
	
	public boolean validateFechaPresentacionProyecto() {
		String fecha = estudiante.getFechaPresentacionProyecto();
		if (estudiante.proyectoEnDesarrollo()) {
			if (!validateEmptyString("fechaPresentacionProyecto", fecha)) {
				return false;
			}
		} else if (fecha != null && !fecha.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				sdf.setLenient(false);
				Date d = sdf.parse(fecha);
				if (!sdf.format(d).equals(fecha)) {
					System.out.println("Formato de fecha inválido (debe ser dd-MM-yyyy)");
					return false;
				}
				if (d.after(new Date())) {
					System.out.println("Fecha inválida (no puede ser mayor que el día de hoy)");
					return false;
				}
			} catch(ParseException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public boolean validateCalificacionProyecto() {
		Float calificacion = estudiante.getCalificacionProyecto();
		if (estudiante.proyectoEnDesarrollo()) {
			if (!validateEmptyFloat("calificacionProyecto", calificacion)) {
				return false;
			}
		} else if (calificacion != null) {
			if (calificacion < 0f || calificacion > 10f) {
				System.out.println("calificacionProyecto tiene un valor inválido (debe ser entre 0.0 y 10.0)");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateNotEmptyString(String attributeName, String attributeValue) {
		if (attributeValue == null || attributeValue.isEmpty()) {
			System.out.println(attributeName + " no puede estar vacío");
			return false;
		}
		return true;
	}
	
	private boolean validateEmptyString(String attributeName, String attributeValue) {
		if (!(attributeValue == null || attributeValue.isEmpty())) {
			System.out.println(attributeName + " debe estar vacío");
			return false;
		}
		return true;
	}
	
	private boolean validateEmptyFloat(String attributeName, Float attributeValue) {
		if (attributeValue != null) {
			System.out.println(attributeName + " debe estar vacío");
			return false;
		}
		return true;
	}
}
