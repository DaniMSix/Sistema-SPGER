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
import sistema.spger.DAO.DAOActividadEntrega;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJActividadEntregaRespuesta;
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
    private TableView<POJActividadEntrega> tvActividadesProgramadas;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcFechaLimiteEntrega;
    @FXML
    private TableColumn tcEstado;
    private ObservableList<POJActividadEntrega> actividades;
    POJUsuario estudianteActual;
    private int idEstudiante = 1;
    private int idCurso = 13;

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
        // TODO
    }
    
    public void configurarTablaUsuarios(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaLimiteEntrega.setCellValueFactory(new PropertyValueFactory("fechaLimiteEntrega"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    public void cargarInformacionTabla() {
        actividades = FXCollections.observableArrayList();
        POJActividadEntregaRespuesta respuestaBD = DAOActividadEntrega.obtenerEntregaActividades(idEstudiante, idCurso);

        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividadesEntregas());
                tvActividadesProgramadas.setItems(actividades);
                break;
        }
    }

    @FXML
    private void dobleClicFila(MouseEvent event) {
        if ( event.getClickCount() == 2){
            tvActividadesProgramadas.getSelectionModel().getSelectedItem().getIdActividad();
            int idActividad = tvActividadesProgramadas.getSelectionModel().getSelectedItem().getIdActividad();
            POJActividadEntrega respuestaBD = DAOActividadEntrega.obtenerActividadPorId(idActividad);
            switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Por el momento no es posible mostrar"
                        + " los datos, inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                irFormulario("Ver detalle", respuestaBD, 0, 0);
                break;
        }
        } 
    }


    @FXML
    private void clicBotonModificar(ActionEvent event) {
        POJActividadEntrega actividadSeleccionada = tvActividadesProgramadas.getSelectionModel().getSelectedItem();
        if(actividadSeleccionada != null){
            irFormulario("Modificar", actividadSeleccionada, 0, 0);
        }else{
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBotonEliminar(ActionEvent event) {
    }
    
    private void irFormulario(String tipoBoton, POJActividadEntrega actividadInformacion, int idEstudiante, int idCurso){

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLFormularioActividad.fxml"));
            Parent vista = loader.load();
            FXMLFormularioActividadController formularioActividad = loader.getController();
            formularioActividad.inicializarInformacionFormulario(tipoBoton, actividadInformacion, idEstudiante, idCurso);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void clicBotonAgregarTarea(ActionEvent event) {
        irFormulario("Registrar", null, idEstudiante, idCurso);
    }
    
    
}
