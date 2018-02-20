package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.Sesion;

public class ControladorLogin{
	@FXML private TextField campoUsuario;
	@FXML private PasswordField campoContrasenia;
	@FXML private Button botonIngresar;
	private ControladorVentanas controladorVentanas;
	
	public ControladorLogin(){
		controladorVentanas = ControladorVentanas.getInstancia();
	}
	
	@FXML public void initialize(){
		campoUsuario.requestFocus();
		validaciones();
		botonIngresar.disableProperty().bind(campoUsuario.textProperty().isEmpty().or(campoContrasenia.textProperty().isEmpty()));
	}
	
	@FXML public void clickIngresar(){
		String alias = campoUsuario.getText();
		String contrasenia = campoContrasenia.getText();
		if(Sesion.iniciar(alias, contrasenia))
			controladorVentanas.cargarPrincipal();
		else{
			controladorVentanas.crearAlerta(AlertType.ERROR, "Error de autenticación. Intenta de nuevo.").show();
		}
	}
	
	private void validaciones(){
		campoUsuario.setContextMenu(new ContextMenu());
		campoUsuario.setOnKeyTyped(event ->{
			if(campoUsuario.getText().length() == 20 || !event.getCharacter().matches("\\w"))
				event.consume();
		});
		
//		campoContrasenia.setOnKeyTyped(event ->{
//			if(!event.getCharacter().matches(""))
//				event.consume();
//		});
	}
}
