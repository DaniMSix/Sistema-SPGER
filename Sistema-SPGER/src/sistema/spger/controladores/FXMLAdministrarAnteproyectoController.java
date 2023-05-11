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
import javafx.fxml.Initializable;
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
        escenarioBase.show();
    }
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("archivoAnteproyecto"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("LGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarDatosTabla(){
        try {
            System.out.println("\n1 no hay error todavia");
            listaAnteproyectos = FXCollections.observableArrayList();
             System.out.println("\n2 no hay error todavia");
            ArrayList<POJAnteproyecto> anteproyectosBD = DAOAnteproyecto.obtenerInformacionAnteproyecto().getAnteproyectos();
                         System.out.println("\n3 no hay error todavia");
                         System.out.println("Codigo respuesta "+DAOAnteproyecto.obtenerInformacionAnteproyecto().getCodigoRespuesta());
            listaAnteproyectos.addAll(anteproyectosBD);
                         System.out.println("\n4 no hay error todavia");
            tvAnteproyectos.setItems(listaAnteproyectos);
                         System.out.println("\n5 no hay error todavia");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }
    
}
