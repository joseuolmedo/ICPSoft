package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
	private static Conexion conexion;
	
	private Conexion(){
		try{
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}
	
	public static Conexion getInstancia(){
		if(conexion == null)
			conexion = new Conexion();
		return conexion;
	}
	
	public Connection conectar() throws SQLException{
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/metricas", "postgres", "piedralunar");
	}
}
