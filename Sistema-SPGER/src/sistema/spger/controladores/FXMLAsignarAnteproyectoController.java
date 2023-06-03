package sistema.spger.controladores;

import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJAnteproyectoRespuesta;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;


public class FXMLAsignarAnteproyectoController implements Initializable {

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
    @FXML
    private TableColumn colNombreDirector;
    @FXML
    private TableColumn colEstadoAnteproyecto;
    private POJUsuario usuarioVerificado;
    private POJRolRespuesta respuestaBD;
    private int idDirectorDeTrabajo;
    ObservableList<POJAnteproyecto> listaAnteproyectos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
    }    
    //Recuperar de la base de datos los anteproyectos aprobados a cargo del director y que no esten asignados     
    @FXML
    private void clicAsignarAnteproyecto(ActionEvent event) {
        POJAnteproyecto anteproyectoAsignar=verificarAnteproyectoSeleccionado();
        if(anteproyectoAsignar!=null){
            try {
                Stage escenarioBase = (Stage) tvAnteproyectos.getScene().getWindow();
                Scene escena = null;
                FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLCapturarInformacionAlumno.fxml"));                 
                Parent vista = loader.load();
                FXMLCapturarInformacionAlumnoController pantallaCaturarAlumno = loader.getController();
                pantallaCaturarAlumno.prepararRolesUsuario(respuestaBD, usuarioVerificado,idDirectorDeTrabajo);
                pantallaCaturarAlumno.prepararAnteproyeto(anteproyectoAsignar);
                escena = new Scene(vista);
                escenarioBase.setScene(escena);
                escenarioBase.setTitle("Capturar informacion alumno");
                escenarioBase.setAlwaysOnTop(false);
                escenarioBase.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla", 
                        Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarDialogoSimple("No hay seleccion", "Seleccione un anteproyecto a asignar", Alert.AlertType.INFORMATION);
        }
    }
    private POJAnteproyecto verificarAnteproyectoSeleccionado(){
        int filaSeleccionada = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0) ? listaAnteproyectos.get(filaSeleccionada):null;
    }
    public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado, int idDirectorDeTrabajo){
        this.usuarioVerificado = usuarioLogueado;
        this.respuestaBD=respuestaBD;   
        this.idDirectorDeTrabajo=idDirectorDeTrabajo;
        cargarDatosTabla();
    }

    private void cargarDatosTabla(){
        try {
            this.listaAnteproyectos = FXCollections.observableArrayList();           
            ArrayList<POJAnteproyecto> anteproyectosBD;
            anteproyectosBD = DAOAnteproyecto.obtenerInfoAnteproyectosParaAsignar(this.idDirectorDeTrabajo).getAnteproyectos();
            System.out.println("Este es el id Del director "+this.idDirectorDeTrabajo);
            listaAnteproyectos.addAll(anteproyectosBD);
            tvAnteproyectos.setItems(listaAnteproyectos);
            System.out.println("Datos cargados");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("boton"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("nombreLGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreEstado"));
    }    
    
}
