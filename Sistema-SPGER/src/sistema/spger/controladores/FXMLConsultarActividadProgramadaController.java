/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLConsultarActividadProgramadaController implements Initializable {

    @FXML
    private TableView<POJActividad> tvActividadesProgramadas;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcFechaLimiteEntrega;
    @FXML
    private TableColumn tcEstado;
    private ObservableList<POJActividad> actividades;
    POJUsuario estudianteActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTablaUsuarios();
        cargarInformacionTabla();
    }    
    
    public void recibirInformacionEstudiante(POJUsuario usuarioLogueado){
        estudianteActual = usuarioLogueado;
    }
    
    public void configurarTablaUsuarios(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaLimiteEntrega.setCellValueFactory(new PropertyValueFactory("fechaLimiteEntrega"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    public void cargarInformacionTabla() {
        actividades = FXCollections.observableArrayList();
        POJActividadRespuesta respuestaBD = DAOActividad.obtenerActividadesProgramadas(44);

        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividades());
                tvActividadesProgramadas.setItems(actividades);
                break;
        }
    }

    @FXML
    private void dobleClicFila(MouseEvent event) {
        if ( event.getClickCount() == 2){
            tvActividadesProgramadas.getSelectionModel().getSelectedItem().getIdActividad();
            int idActividad = tvActividadesProgramadas.getSelectionModel().getSelectedItem().getIdActividad();
            System.out.println("idActividad" + idActividad);
            POJActividad respuestaBD = DAOActividad.obtenerActividadPorId(idActividad);
            
            switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Por el momento no es posible mostrar"
                        + " los datos, inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                irFormulario("Ver detalle", respuestaBD);
                break;
        }
        } 
    }


    @FXML
    private void clicBotonModificar(ActionEvent event) {
        POJActividad actividadSeleccionada = tvActividadesProgramadas.getSelectionModel().getSelectedItem();
        if(actividadSeleccionada != null){
            irFormulario("Modificar", actividadSeleccionada);
        }else{
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBotonEliminar(ActionEvent event) {
    }
    
    private void irFormulario(String tipoBoton, POJActividad actividadInformacion){

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLFormularioActividad.fxml"));
            Parent vista = loader.load();
            FXMLFormularioActividadController formularioActividad = loader.getController();
            formularioActividad.inicializarInformacionFormulario(tipoBoton, actividadInformacion);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            //escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void clicBotonAgregarTarea(ActionEvent event) {
        POJActividad actividadSeleccionada = tvActividadesProgramadas.getSelectionModel().getSelectedItem();
        irFormulario("Registrar", actividadSeleccionada);
    }
    
    
    
}
