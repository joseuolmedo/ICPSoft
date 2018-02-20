package controlador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;



public class ControladorPrincipal{
	@FXML BorderPane rootPrincipal;
	
	@FXML public void initialize(){}
	
	@FXML private void clickUsuarios(){
		try{
			rootPrincipal.setCenter(FXMLLoader.load(getClass().getResource("/vista/Contro_Usuarios.fxml")));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			}		
		}
	
	@FXML public void clickProyectos() {
		try {
			rootPrincipal.setCenter(FXMLLoader.load(getClass().getResource("/vista/Proyectos.fxml")));
	 	} 
	 	catch (Exception ioe){
	 		ioe.printStackTrace();
	 		}
	}
}
