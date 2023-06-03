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
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
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
    POJRolRespuesta respuestaBD;
    POJUsuario usuarioVerificado;
    private int idDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
    }    



    
    private void btnAgregarAnteproyecto(ActionEvent event) {
        
    } 

    @FXML
    private void btnBack(ActionEvent event) {
    }

    @FXML
    private void clicAgregarAnteproyecto(ActionEvent event) throws IOException {
        Stage escenarioBase = (Stage) btnAgregarAnteproyecto.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLAgregarAnteproyecto.fxml"));        
        try{
        Parent vista = loader.load();
        FXMLAgregarAnteproyectoController pantallaAgregarAnteproyecto = loader.getController();
        pantallaAgregarAnteproyecto.prepararRolesUsuario(respuestaBD, usuarioVerificado,idDirector);       
        escena = new Scene(vista);
        escenarioBase.setScene(escena);
        escenarioBase.setTitle("Agregar anteproyecto");
        escenarioBase.setAlwaysOnTop(false);
        escenarioBase.show();
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "+
                    "intentelo mas tarde", Alert.AlertType.ERROR);            
        }
    }
    public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado,int idDirectorDeTrabajo){
        this.usuarioVerificado = usuarioLogueado;
        this.respuestaBD=respuestaBD;
        this.idDirector= idDirectorDeTrabajo;
        cargarDatosTabla();
    }    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("boton"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("nombreLGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreEstado"));
    }
    
    private void cargarDatosTabla(){
        try {
            listaAnteproyectos = FXCollections.observableArrayList();
            System.out.println("Este es el id Del director "+this.idDirector);            
            ArrayList<POJAnteproyecto> anteproyectosBD = DAOAnteproyecto.obtenerInfoAnteproyectoPorDirector(this.idDirector).getAnteproyectos();
            listaAnteproyectos.addAll(anteproyectosBD);
            tvAnteproyectos.setItems(listaAnteproyectos);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clicModificar(ActionEvent event) {
        POJAnteproyecto anteproyectoEdicion = verificarAnteproyectoSeleccionado();
        if(anteproyectoEdicion!=null){
            if(anteproyectoEdicion.getIdEstado()==4)
                irFormulario(anteproyectoEdicion);
            else{
                Utilidades.mostrarDialogoSimple("Advertencia", 
                "Solo puede seleccionar anteproyectos con estado <<GUARDADO>>", Alert.AlertType.WARNING);
                }
        }else
            Utilidades.mostrarDialogoSimple("Selección obligatoria", 
                    "Debes seleccionar algún registro de la tabla para su edición", Alert.AlertType.WARNING);
    }

    @FXML
    private void clicEliminar(ActionEvent event) throws SQLException {
        POJAnteproyecto anteSeleccionado = verificarAnteproyectoSeleccionado();
        int respuesta=Constantes.ERROR_CONEXION;
        if(anteSeleccionado!=null){
           if(anteSeleccionado.getIdEstado()==4)
                respuesta = DAOAnteproyecto.eliminarAnteproyecto(anteSeleccionado);
                if(respuesta==Constantes.OPERACION_EXITOSA){
                    Utilidades.mostrarDialogoSimple("Anteproyecto Eliminado", 
                            "El anteproyecto se elimino correctamente", Alert.AlertType.INFORMATION);
                }
            else{
                Utilidades.mostrarDialogoSimple("Advertencia", 
                "Solo puede seleccionar anteproyectos con estado <<GUARDADO>>", Alert.AlertType.WARNING);
                }
        }else
            Utilidades.mostrarDialogoSimple("Selección obligatoria", 
                    "Debes seleccionar algún registro de la tabla para su eliminación", Alert.AlertType.WARNING);
    }
    private POJAnteproyecto verificarAnteproyectoSeleccionado(){
        int filaSeleccionada = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0) ? listaAnteproyectos.get(filaSeleccionada):null;
    }

    private void irFormulario(POJAnteproyecto anteproyecto){
        try {
            Stage escenarioBase = (Stage) btnAgregarAnteproyecto.getScene().getWindow();
            Scene escena = null;
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLAgregarAnteproyecto.fxml")); 
            Parent vista = loader.load();
            FXMLAgregarAnteproyectoController pantallaEditarAnteproyecto = loader.getController();
            pantallaEditarAnteproyecto.prepararRolesUsuario(respuestaBD, usuarioVerificado,idDirector);
            pantallaEditarAnteproyecto.prepararAnteproyetoEdicion(anteproyecto);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Editar anteproyecto");
            escenarioBase.setAlwaysOnTop(false);
            escenarioBase.show();
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicPostular(ActionEvent event) throws SQLException {
        POJAnteproyecto anteproyectoEdicion = verificarAnteproyectoSeleccionado();
        if(anteproyectoEdicion!=null){
            if(anteproyectoEdicion.getIdEstado()==4){
                anteproyectoEdicion.setIdEstado(1);
                DAOAnteproyecto.modificarAnteproyecto(anteproyectoEdicion);
                Utilidades.mostrarDialogoSimple("Anteproyecto Postulado", "El anteproyecto se ha postulado correctamente", Alert.AlertType.INFORMATION);
                cargarDatosTabla();
            }else{
                Utilidades.mostrarDialogoSimple("Advertencia", 
                "Solo puede seleccionar anteproyectos con estado <<GUARDADO>>", Alert.AlertType.WARNING);
                }
        }else
            Utilidades.mostrarDialogoSimple("Selección obligatoria", 
                    "Debes seleccionar algún registro de la tabla para su edición", Alert.AlertType.WARNING);
    }
}
