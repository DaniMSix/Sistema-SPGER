/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistema.spger.controladores;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.utils.Utilidades;

public class FXMLAdministrarAnteproyectoController implements Initializable {

    @FXML
    private Button btnAgregarAnteproyecto;
    @FXML
    private TableView<POJAnteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colDocumento;
    @FXML
    private TableColumn colLGAC;
    
    ObservableList<POJAnteproyecto> listaAnteproyectos;
    @FXML
    private TableColumn colNombreDirector;
    @FXML
    private TableColumn colEstadoAnteproyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarDatosTabla();
    }    



    
    private void btnAgregarAnteproyecto(ActionEvent event) {
        
    } 

    @FXML
    private void btnBack(ActionEvent event) {
    }

    @FXML
    private void clicAgregarAnteproyecto(ActionEvent event) throws IOException {
        Stage escenarioBase = (Stage) btnAgregarAnteproyecto.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLAgregarAnteproyecto.fxml"));
        escenarioBase.setTitle("Agregar anteproyecto");
        escenarioBase.setAlwaysOnTop(false);
        escenarioBase.show();
    }
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("boton"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("LGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarDatosTabla(){
        try {
            listaAnteproyectos = FXCollections.observableArrayList();
            ArrayList<POJAnteproyecto> anteproyectosBD = DAOAnteproyecto.obtenerInformacionAnteproyecto().getAnteproyectos();
            listaAnteproyectos.addAll(anteproyectosBD);
            tvAnteproyectos.setItems(listaAnteproyectos);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clicModificar(ActionEvent event) {
        POJAnteproyecto anteproyectoEdicion = verificarAnteproyectoSeleccionado();
        if(anteproyectoEdicion!=null)
            irFormulario(anteproyectoEdicion);
        else
            Utilidades.mostrarDialogoSimple("Selección obligatoria", 
                    "Debes seleccionar algún registro de la tabla para su edición", Alert.AlertType.WARNING);
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }
    private POJAnteproyecto verificarAnteproyectoSeleccionado(){
        int filaSeleccionada = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0) ? listaAnteproyectos.get(filaSeleccionada):null;
    }
    private void irFormulario(POJAnteproyecto anteproyecto){
        try {
            System.out.println("En ir formulario");
        Stage escenarioNuevo = (Stage) btnAgregarAnteproyecto.getScene().getWindow();
            escenarioNuevo.setScene(Utilidades.inicializarEscena("vistas/FXMLAgregarAnteproyecto.fxml"));
            escenarioNuevo.setTitle("Modficiar anteproyecto");
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);
        }
    }
}
