package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOActividadEntrega;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJActividadEntregaRespuesta;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLConsultarEntregasController implements Initializable {

    @FXML
    private TableView<POJActividadEntrega> tvEntregas;
    @FXML
    private TableColumn tcNombreActividad;
    @FXML
    private TableColumn tcFechaLimiteEntrega;
    @FXML
    private TableColumn tcEstado;
    @FXML
    private TableColumn tcCalificacion;
    private ObservableList<POJActividadEntrega> actividadesEntregas;
    private int idEstudiante = 1;
    private int idCurso = 13;
    List<POJActividadEntrega> listaIdActividades = new ArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaActividadesEntregas();
        cargarInformacionTabla();
        
    }    
    
    public void inicializarInformacion(){
        idEstudiante = 1;
        idCurso = 13;
        
        /*
        for (POJCursoActividad elemento : listaIdActividades) {
            System.out.println(elemento.getIdActividad());
        }
        */
        
    }
    
    public void configurarTablaActividadesEntregas(){
        tcNombreActividad.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaLimiteEntrega.setCellValueFactory(new PropertyValueFactory("fechaLimiteEntrega"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        tcCalificacion.setCellValueFactory(new PropertyValueFactory("calificacion"));
    }
    
    public void cargarInformacionTabla() {
        actividadesEntregas = FXCollections.observableArrayList();
        POJActividadEntregaRespuesta respuestaBD = DAOActividadEntrega.obtenerEntregaActividades(idEstudiante, idCurso);
        
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividadesEntregas.addAll(respuestaBD.getActividadesEntregas());
                tvEntregas.setItems(actividadesEntregas);
                break;
        }
    }

    @FXML
    private void clicBotonEntregar(ActionEvent event) {
        POJActividadEntrega actividadSeleccionada = tvEntregas.getSelectionModel().getSelectedItem();
        System.err.println("Nombre " + actividadSeleccionada.getNombre());
        System.err.println("Nombre " + actividadSeleccionada.getDescripcion());
        System.err.println("id actividad" + actividadSeleccionada.getIdActividad());
        if(actividadSeleccionada != null){
            irFormulario("Registrar", actividadSeleccionada);
        }
        else{
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
       
    }

    @FXML
    private void clicBotonModificar(ActionEvent event) {
        POJActividadEntrega actividadSeleccionada = tvEntregas.getSelectionModel().getSelectedItem();
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
    
    private void irFormulario(String tipoBoton, POJActividadEntrega actividadInformacion){

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLFormularioEntrega.fxml"));
            Parent vista = loader.load();
            FXMLFormularioEntregaController formularioActividad = loader.getController();
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

    
}
