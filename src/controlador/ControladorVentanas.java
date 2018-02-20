package controlador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControladorVentanas{
	private Stage escenarioPrincipal;
	private static ControladorVentanas instancia;
	
	private ControladorVentanas(){}
	
	public static ControladorVentanas getInstancia(){
		if(instancia != null)
			return instancia;
		else{
			return instancia = new ControladorVentanas();
		}
	}
	
	public void setEscenarioPrincipal(Stage escenarioPrincipal){
		this.escenarioPrincipal = escenarioPrincipal;
	}
	
	public void cargarPrincipal(){
		try{
			escenarioPrincipal.hide();
			escenarioPrincipal.setScene(new Scene(FXMLLoader.load(getClass().getResource("/vista/Principal.fxml"))));
			escenarioPrincipal.show();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void cargarLogin(){
		try{
			escenarioPrincipal.hide();
			escenarioPrincipal.setScene(new Scene(FXMLLoader.load(getClass().getResource("/vista/Login2.fxml"))));
			escenarioPrincipal.show();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public Alert crearAlerta(AlertType tipo, String mensaje){
		Alert alerta = new Alert(tipo, mensaje);
		alerta.initOwner(escenarioPrincipal);
		alerta.setHeaderText(null);
		return alerta;
	}
}
