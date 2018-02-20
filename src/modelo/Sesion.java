package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sesion{
	private static Usuario usuarioActual = null;
	private static Conexion conexion = Conexion.getInstancia();
	
	private Sesion(){}
	
	public static Usuario getUsuarioActual(){
		return usuarioActual;
	}
	
	public static boolean iniciar(String alias, String contrasenia){
		String sql = "SELECT * FROM usuario WHERE alias = ? AND contrasenia = ? AND estatus = true";
		try(Connection connection = conexion.conectar(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, alias);
			preparedStatement.setString(2, contrasenia);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				String nombre = resultSet.getString("nombre");
				String tipoUsuario = resultSet.getString("tipo_usuario");
				usuarioActual = new Usuario(nombre, alias, contrasenia, tipoUsuario, true);
				return true;
			}
			else
				return false;
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	public static void terminar(){
		usuarioActual = null;
	}
}
