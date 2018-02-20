package controlador;

import java.sql.SQLException;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Usuario;
import modelo.UsuarioDAO;

public class ControladorUsuarios{
	@FXML private Button botonNuevo, botonModificar, botonEliminar, botonGuardar, botonCancelar;
	@FXML private TextField campoBuscar, campoNombre, campoAlias;
	@FXML private PasswordField campoContrasenia, campoRepetirContrasenia;
	@FXML private ComboBox<String> comboBoxNivel, comboBoxEstado;
	@FXML private TableView<Usuario> tablaUsuarios;
	@FXML private TableColumn<Usuario, String> columnaEstado;
	
	private UsuarioDAO usuarioDao;
	private boolean nuevo, modificar;
	private Usuario usuarioaModificar;
	private ControladorVentanas controladorVentanas;
	
	public ControladorUsuarios(){
		usuarioaModificar = null;
		usuarioDao = new UsuarioDAO();
		controladorVentanas = ControladorVentanas.getInstancia();
	}
	
	@FXML public void initialize(){
		desactivarCampos(true);
		botonModificar.setDisable(true);
		botonEliminar.setDisable(true);
		botonCancelar.setDisable(true);
		botonGuardar.setDisable(true);
		
		ObservableList<String> niveles = FXCollections.observableArrayList("Miembro SQA", "Líder SQA", "Administrador");
		comboBoxNivel.setItems(niveles);
		
		ObservableList<String> estados = FXCollections.observableArrayList("Activo", "Inactivo");
		comboBoxEstado.setItems(estados);
		
//		columnaEstado.setCellValueFactory(new Callback<CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
//		     public ObservableValue<String> call(CellDataFeatures<Usuario, String> p) {
//		    	 if(p.getValue().getEstado())
//		    		 return new SimpleStringProperty("Activo");
//		    	 else
//		    		 return new SimpleStringProperty("Inactivo");
//		     }
//		});
		
		FilteredList<Usuario> listaFiltrada = new FilteredList<>(usuarioDao.getListaUsuarios(), p ->true);
		campoBuscar.textProperty().addListener((observable, anterior, nuevo) ->{
			listaFiltrada.setPredicate(usuario ->{
				if(nuevo == null || nuevo.isEmpty()){
					return true;
				}
				String nuevoMinusculas = nuevo.toLowerCase();
				if(usuario.getAlias().toLowerCase().contains(nuevoMinusculas))
					return true;
				if(usuario.getNombre().toLowerCase().contains(nuevoMinusculas))
					return true;
				
				return false;
			});
		});
		
		SortedList<Usuario> listaOrdenada = new SortedList<>(listaFiltrada);
		listaOrdenada.comparatorProperty().bind(tablaUsuarios.comparatorProperty());
		tablaUsuarios.setItems(listaOrdenada);
		
		tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(e ->{
			Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
			if(!nuevo && !modificar){
				if(seleccion != null){				
					campoAlias.setText(seleccion.getAlias());
					campoContrasenia.setText(seleccion.getContrasenia());
					campoRepetirContrasenia.setText(seleccion.getContrasenia());
					campoNombre.setText(seleccion.getNombre());
					comboBoxNivel.getSelectionModel().select(seleccion.getNivel());
					comboBoxEstado.getSelectionModel().select(seleccion.getEstado() ? "Activo" : "Inactivo");
					botonModificar.setDisable(false);
					botonEliminar.setDisable(false);
				}
				else{
					limpiarCampos();
					botonModificar.setDisable(true);
					botonEliminar.setDisable(true);
				}
			}
		});
	}
	
	@FXML private void clickNuevo(){
		nuevo = true;
		
		botonNuevo.setDisable(true);
		botonModificar.setDisable(true);
		botonEliminar.setDisable(true);
		
		desactivarCampos(false);
		limpiarCampos();
		
		BooleanBinding bb = new BooleanBinding() {
		    {
		        super.bind(campoNombre.textProperty(), campoAlias.textProperty(),
		                campoContrasenia.textProperty(), campoRepetirContrasenia.textProperty(),		                
		                comboBoxNivel.getSelectionModel().selectedItemProperty(),
		        		comboBoxEstado.getSelectionModel().selectedItemProperty());
		    }
		    @Override
		    protected boolean computeValue() {
		        return (campoNombre.getText().isEmpty() || campoAlias.getText().isEmpty()
		                || campoContrasenia.getText().isEmpty() || campoRepetirContrasenia.getText().isEmpty()
		        		|| comboBoxNivel.getSelectionModel().getSelectedItem() == null || comboBoxEstado.getSelectionModel().getSelectedItem() == null);
		    }
		};
		botonGuardar.disableProperty().bind(bb);
		botonCancelar.setDisable(false);
		
		campoNombre.requestFocus();
	}
	
	@FXML private void clickModificar(){
		modificar = true;
		usuarioaModificar = tablaUsuarios.getSelectionModel().getSelectedItem();
		
		desactivarCampos(false);
		campoAlias.setDisable(true);
		
		botonNuevo.setDisable(true);
		botonEliminar.setDisable(true);
		botonModificar.setDisable(true);		
		
		BooleanBinding bb = new BooleanBinding() {
		    {
		        super.bind(campoNombre.textProperty(), campoAlias.textProperty(),
		                campoContrasenia.textProperty(), campoRepetirContrasenia.textProperty(),		                
		                comboBoxNivel.getSelectionModel().selectedItemProperty(),
		        		comboBoxEstado.getSelectionModel().selectedItemProperty());
		    }
		    @Override
		    protected boolean computeValue() {
		        return (campoNombre.getText().isEmpty() || campoAlias.getText().isEmpty()
		                || campoContrasenia.getText().isEmpty() || campoRepetirContrasenia.getText().isEmpty()
		        		|| comboBoxNivel.getSelectionModel().getSelectedItem() == null || comboBoxEstado.getSelectionModel().getSelectedItem() == null);
		    }
		};
		botonGuardar.disableProperty().bind(bb);
		botonCancelar.setDisable(false);
		
		campoNombre.requestFocus();
	}
	
	@FXML private void clickEliminar(){
		String mensaje = "¿Seguro que quieres eliminar este usuario?";
		controladorVentanas.crearAlerta(AlertType.CONFIRMATION, mensaje).showAndWait().ifPresent(response -> {
		     if (response == ButtonType.OK) {
		    	 usuarioDao.eliminar(tablaUsuarios.getSelectionModel().getSelectedItem());
		     }
		});
	}
	
	@FXML private void clickGuardar(){
		boolean registroValido = false;
		
		String nombre = campoNombre.getText().trim();
		String alias = campoAlias.getText().trim();
		String contrasenia = campoContrasenia.getText().trim();
		String contrasenia2 = campoRepetirContrasenia.getText().trim();

		String nivel = comboBoxNivel.getSelectionModel().getSelectedItem();
		boolean estado = comboBoxEstado.getSelectionModel().getSelectedItem().equals("Activo") ? true : false;
		if(nombre != null && !nombre.isEmpty())
			if(alias != null && !alias.isEmpty())
				if(contrasenia != null && !contrasenia.isEmpty())
					if(contrasenia2 != null && !contrasenia2.isEmpty())						
						if(nivel != null)
							if(!comboBoxEstado.getSelectionModel().isEmpty())
								if(contrasenia.equals(contrasenia2))
									registroValido = true;
								else{
									controladorVentanas.crearAlerta(AlertType.ERROR, "Las contraseñas deben coincidir.").show();
									campoContrasenia.requestFocus();
								}
							else{
								controladorVentanas.crearAlerta(AlertType.ERROR, "Selecciona estado.").show();
								comboBoxEstado.requestFocus();
							}
						else{
							controladorVentanas.crearAlerta(AlertType.ERROR, "Selecciona nivel de acceso.").show();
							comboBoxNivel.requestFocus();
						}						
					else{
						controladorVentanas.crearAlerta(AlertType.ERROR, "Confirma la contraseña.").show();
						campoRepetirContrasenia.requestFocus();
					}
				else{
					controladorVentanas.crearAlerta(AlertType.ERROR, "El campo contraseña es requerido.").show();
					campoContrasenia.requestFocus();
				}
			else{
				controladorVentanas.crearAlerta(AlertType.ERROR, "El campo alias es requerido.").show();
				campoAlias.requestFocus();
			}
		else{
			controladorVentanas.crearAlerta(AlertType.ERROR, "El campo nombre es requerido.").show();
			campoNombre.requestFocus();
		}
		
		if(registroValido){			
			Usuario nuevoUsuario = new Usuario(nombre, alias, contrasenia, nivel, estado);
			
			try{
				if(nuevo){
					usuarioDao.agregar(nuevoUsuario);
				}
				else
					if(modificar){
						usuarioDao.modificar(usuarioaModificar, nuevoUsuario);
						tablaUsuarios.refresh();
					}
				clickCancelar();
			}
			catch(SQLException sqle){
				controladorVentanas.crearAlerta(Alert.AlertType.INFORMATION, String.format("Ya existe un usuario con alias \"%s\"", alias)).show();
				campoAlias.requestFocus();
			}
		}
	}
	
	@FXML private void clickCancelar(){
		limpiarCampos();
		desactivarCampos(true);
		botonCancelar.setDisable(true);
		botonNuevo.setDisable(false);
		botonGuardar.disableProperty().unbind();
		botonGuardar.setDisable(true);
		nuevo = modificar = false;
		usuarioaModificar = null;
		Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
		if(seleccion != null){
			campoAlias.setText(seleccion.getAlias());
			campoContrasenia.setText(seleccion.getContrasenia());
			campoRepetirContrasenia.setText(seleccion.getContrasenia());
			campoNombre.setText(seleccion.getNombre());
			comboBoxNivel.getSelectionModel().select(seleccion.getNivel());
			comboBoxEstado.getSelectionModel().select(seleccion.getEstado() ? "Activo" : "Inactivo");
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}
	
	private void desactivarCampos(boolean valor){
		campoNombre.setDisable(valor);
		campoAlias.setDisable(valor);
		campoContrasenia.setDisable(valor);
		campoRepetirContrasenia.setDisable(valor);
		comboBoxNivel.setDisable(valor);
		comboBoxEstado.setDisable(valor);
	}
	
	private void limpiarCampos(){
		campoNombre.clear();
		campoAlias.clear();
		campoContrasenia.clear();
		campoRepetirContrasenia.clear();
		comboBoxNivel.getSelectionModel().clearSelection();
		comboBoxEstado.getSelectionModel().clearSelection();
	}
}
