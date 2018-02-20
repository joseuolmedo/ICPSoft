package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioDAO{
	private Conexion conexion;
	private ObservableList<Usuario> listaUsuarios;
	
	public UsuarioDAO(){
		conexion = Conexion.getInstancia();
		listaUsuarios = FXCollections.observableArrayList();
		leerUsuarios();
	}
	
	public ObservableList<Usuario> getListaUsuarios(){
		return listaUsuarios;
	}
	
	public void leerUsuarios(){
		listaUsuarios.clear();
		String sql = "SELECT * FROM usuario";
		try(Connection connection = conexion.conectar(); Statement statement = connection.createStatement(); ResultSet resultset = statement.executeQuery(sql)){
			while(resultset.next()){
				String nombre = resultset.getString("nombre");
				String alias = resultset.getString("alias");
				String contrasenia = resultset.getString("contrasenia");
				String tipoUsuario = resultset.getString("nivel");
				boolean estado = resultset.getBoolean("estado");
				listaUsuarios.add(new Usuario(alias, contrasenia, nombre, tipoUsuario, estado));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void agregar(Usuario nuevo) throws SQLException{
		String sql= "INSERT INTO usuario(nombre, alias, contrasenia, nivel, estado) VALUES(?, ?, ?, ?, ?)";
		try(Connection connection = conexion.conectar(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, nuevo.getNombre());
			preparedStatement.setString(2, nuevo.getAlias());
			preparedStatement.setString(3, nuevo.getContrasenia());
			preparedStatement.setString(4, nuevo.getNivel());
			preparedStatement.setBoolean(5, nuevo.getEstado());
			preparedStatement.executeUpdate();
			listaUsuarios.add(nuevo);
		}
	}
	
	public void modificar(Usuario anterior, Usuario nuevo) throws SQLException{
		String sql= "UPDATE usuario SET nombre = ?, contrasenia = ?, nivel = ?, estado = ? WHERE alias = ?";
		try(Connection connection = conexion.conectar(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, nuevo.getNombre());
			preparedStatement.setString(2, nuevo.getContrasenia());
			preparedStatement.setString(3, nuevo.getNivel());
			preparedStatement.setBoolean(4, nuevo.getEstado());
			preparedStatement.setString(5, anterior.getAlias());
			preparedStatement.executeUpdate();
			anterior.setNombre(nuevo.getNombre());
			anterior.setContrasenia(nuevo.getContrasenia());
			anterior.setNivel(nuevo.getNivel());
			anterior.setEstado(nuevo.getEstado());
		}
	}
	
	public void eliminar(Usuario usuarioEliminado){
		String sql = String.format("DELETE from usuario WHERE alias = '%s'", usuarioEliminado.getAlias());
		try(Connection connection = conexion.conectar(); Statement statement = connection.createStatement()){
			statement.executeUpdate(sql);
			listaUsuarios.remove(usuarioEliminado);
		}
		catch(SQLException e){			
			e.printStackTrace();
		}
	}
}
