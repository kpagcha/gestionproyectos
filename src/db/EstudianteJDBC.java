package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import models.Estudiante;

public class EstudianteJDBC {
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/gestionproyectos";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	public List<Estudiante> allEstudiantes() {
		List<Estudiante> estudiantes = new ArrayList<Estudiante>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		String sql = "select * from gestionproyectos.estudiantes";
		
		try {
			connection = getDBConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				Estudiante e = new Estudiante();
				setEstudianteAttributes(e, rs);
				estudiantes.add(e);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return estudiantes;
	}
	
	public Estudiante getEstudiante(String key) {
		Estudiante estudiante = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		key = replaceQuotes(key);
		String sql = "select * from gestionproyectos.estudiantes where " +
				"(concat(primerApellido, segundoApellido) = ?) or " + 
				"(primerApellido = ? and segundoApellido is null)";
		
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(sql);
			key = replaceQuotes(key);
			statement.setString(1, key);
			statement.setString(2, key);
			rs = statement.executeQuery();
			if (rs.next()) {
				estudiante = new Estudiante();
				setEstudianteAttributes(estudiante, rs);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return estudiante;
	}
	
	public Estudiante createEstudiante(Estudiante src) {
		Estudiante estudiante = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		String sql = "insert into gestionproyectos.estudiantes (nombre, primerApellido, " +
				"segundoApellido, tituloProyecto, tutor1, tutor2, estadoProyecto, " +
				"fechaPresentacionProyecto, calificacionProyecto) values " +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, replaceQuotes(src.getNombre()));
			statement.setString(2, replaceQuotes(src.getPrimerApellido()));
			statement.setString(3, replaceQuotes(src.getSegundoApellido()));
			statement.setString(4, replaceQuotes(src.getTituloProyecto()));
			statement.setString(5, replaceQuotes(src.getTutor1()));
			statement.setString(6, replaceQuotes(src.getTutor2()));
			statement.setString(7, replaceQuotes(src.getEstadoProyecto()));
			statement.setString(8, replaceQuotes(src.getFechaPresentacionProyecto()));
			setCalificacionProyecto(statement, 9, src);
			
			if (statement.executeUpdate() == 1) {
				estudiante = src;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return estudiante;
	}
	
	public Estudiante updateEstudiante(String key, Estudiante src) {
		Estudiante estudiante = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			String sql = construirUpdateQuery(key);
			connection = getDBConnection();
			statement = connection.prepareStatement(sql);
			setParametersUpdateStatement(statement, src, key);
			
			if (statement.executeUpdate() == 1) {
				estudiante = getEstudiante(key);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return estudiante;
	}
	
	public boolean deleteEstudiante(Estudiante estudiante) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		String sql = "delete from gestionproyectos.estudiantes where " + 
				"primerApellido = ? and (segundoApellido = ? or segundoApellido is null)";
		
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, replaceQuotes(estudiante.getPrimerApellido()));
			statement.setString(2, replaceQuotes(estudiante.getSegundoApellido()));
			
			if (statement.executeUpdate() >= 1) {
				return true;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
	
	private String construirUpdateQuery(String key) {
		String sql = "update gestionproyectos.estudiantes set ";
		String[] atributosActualizables = Estudiante.getAtributosActualizables();
		
		for (int i = 0; i < atributosActualizables.length; i++) {
			String atributo = atributosActualizables[i];
			sql += atributo + " = ?";
			if (i < atributosActualizables.length - 1) sql += ", ";
		}
		sql += " where (concat(primerApellido, segundoApellido) = ?) or (primerApellido = ? and segundoApellido is null)";
		return sql;
	}
	
	private void setParametersUpdateStatement(PreparedStatement statement, Estudiante src, String key) {
		try {
			Class<?> claseEstudiante = Class.forName("models.Estudiante");
			String[] atributosActualizables = Estudiante.getAtributosActualizables();
			for (int i = 0; i < atributosActualizables.length; i++) {
				String atributo = atributosActualizables[i];
				String nombreMetodoGetter = "get" + atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
				Method metodoGetter = claseEstudiante.getDeclaredMethod(nombreMetodoGetter);
				if (atributo.equals("calificacionProyecto")) {
					setCalificacionProyecto(statement, i + 1, src);
				} else {
					statement.setString(i + 1, replaceQuotes(((String)metodoGetter.invoke(src))));
				}
			}
			key = replaceQuotes(key);
			statement.setString(atributosActualizables.length + 1, key);
			statement.setString(atributosActualizables.length + 2, key);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(NoSuchMethodException e) {
			System.out.println(e.getMessage());
		} catch(IllegalAccessException e) {
			System.out.println(e.getMessage());
		} catch(InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void setEstudianteAttributes(Estudiante e, ResultSet rs) throws SQLException {
		String nombre = rs.getString("nombre");
		String primerApellido = rs.getString("primerApellido");
		String segundoApellido = rs.getString("segundoApellido");
		String tituloProyecto = rs.getString("tituloProyecto");
		String tutor1 = rs.getString("tutor1");
		String tutor2 = rs.getString("tutor2");
		String estadoProyecto = rs.getString("estadoProyecto");
		String fechaPresentacionProyecto = rs.getString("fechaPresentacionProyecto");
		Float calificacionProyecto = getCalificacionProyecto(rs);
		
		e.setNombre(nombre);
		e.setPrimerApellido(primerApellido);
		e.setSegundoApellido(segundoApellido);
		e.setTituloProyecto(tituloProyecto);
		e.setTutor1(tutor1);
		e.setTutor2(tutor2);
		e.setEstadoProyecto(estadoProyecto);
		e.setFechaPresentacionProyecto(fechaPresentacionProyecto);
		e.setCalificacionProyecto(calificacionProyecto);
	}
	
	private Float getCalificacionProyecto(ResultSet rs) throws SQLException {
		Float calificacion = rs.getFloat("calificacionProyecto");
		if (rs.wasNull()) {
			calificacion = null;
		}
		return calificacion;
	}
	
	private void setCalificacionProyecto(PreparedStatement statement, int pos, Estudiante src) throws SQLException {
		Float calificacion = src.getCalificacionProyecto();
		if (calificacion == null) {
			statement.setNull(pos, Types.FLOAT);
		} else {
			statement.setFloat(pos, calificacion.floatValue());
		}
	}
	
	private Connection getDBConnection() {
		Connection connection = null;
		
		try {
			Class.forName(DB_DRIVER);
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}

		return connection;
	}
	
	private String replaceQuotes(String key) {
		if (key == null) {
			return null;
		}
		return key.replace("'", "\'");
	}
}
