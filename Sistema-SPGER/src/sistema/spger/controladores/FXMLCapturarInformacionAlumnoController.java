package sistema.spger.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sistema.spger.DAO.DAOEstudiante;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJEstudiante;
import sistema.spger.modelo.POJO.POJEstudianteRespuesta;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLCapturarInformacionAlumnoController implements Initializable {

    private POJRolRespuesta respuestaBD;
    private POJUsuario usuarioVerificado;
    private POJEstudianteRespuesta estudianteRespuesta;
    private int idDirectorDeTrabajo;
    private POJAnteproyecto anteproyectoAsignar;
    private ObservableList<POJEstudiante> items;
    @FXML
    private ListView<POJEstudiante> lvEstudiantes;
    @FXML
    private Label lbNombreAnteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("ANTES DEL ERRORRR");
        prepararDatosLista();
    }    
    private void prepararDatosLista(){
        try {
            this.estudianteRespuesta=DAOEstudiante.obtenerEstudiantesSinAnteproyecto();
            items=FXCollections.observableArrayList(estudianteRespuesta.getEstudiantes());          
            lvEstudiantes.setItems(items);
        } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
            Utilidades.mostrarDialogoSimple("Error de conexion", 
                    "En este momento no hay conexion con la base de datos "
                    , Alert.AlertType.ERROR);
        }
    }
    void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioVerificado, int idDirectorDeTrabajo) {
        this.respuestaBD=respuestaBD;
        this.usuarioVerificado=usuarioVerificado;
        this.idDirectorDeTrabajo=idDirectorDeTrabajo;
    }

    void prepararAnteproyeto(POJAnteproyecto anteproyectoAsignar){
        this.anteproyectoAsignar=anteproyectoAsignar;
        lbNombreAnteproyecto.setText(this.anteproyectoAsignar.getNombreAnteproyecto());
    }
    private POJEstudiante obtenerSeleccion(){
        return lvEstudiantes.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void clicAsignar(ActionEvent event) throws SQLException {
        if(obtenerSeleccion()!=null){
            int respuesta= DAOEstudiante.asignarAnteproyecto(this.anteproyectoAsignar.getIdAnteproyecto(),obtenerSeleccion().getIdEstudiante());
                System.out.println(respuesta);            
            if(respuesta==Constantes.OPERACION_EXITOSA){
                 Utilidades.mostrarDialogoSimple("Operacion exitosa", 
                   "El anteproyecto "+this.anteproyectoAsignar.getNombreAnteproyecto()+
                           "se asigno al estudiante"+obtenerSeleccion().getNombreEstudiante(), 
                   Alert.AlertType.INFORMATION);
            }
        }
        else
           Utilidades.mostrarDialogoSimple("No hay seleccion", 
                   "Por favor seleccione un estudiante", 
                   Alert.AlertType.WARNING);
    }
    
}
