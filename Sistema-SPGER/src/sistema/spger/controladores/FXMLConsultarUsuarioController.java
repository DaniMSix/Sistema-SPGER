package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOUsuario;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.modelo.POJO.POJUsuarioRespuesta;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLConsultarUsuarioController implements Initializable {

    @FXML
    private TableView<POJUsuario> tvUsuarios;
    @FXML
    private TableColumn tcRol;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcContrasenia;
    
    private ObservableList<POJUsuario> usuarios;
    
    @FXML
    private TextField txfBuscarPorNombre;
    
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txfBuscarRol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.configurarTablaUsuarios();
        this.cargarInformacionTabla();
    }

    public void configurarTablaUsuarios(){
        tcRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        tcContrasenia.setCellValueFactory(new PropertyValueFactory("contrasenia"));
    }
    
    public void cargarInformacionTabla() {
        usuarios = FXCollections.observableArrayList();
        POJUsuarioRespuesta respuestaBD = DAOUsuario.obtenerUsuarios();

        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                usuarios.addAll(respuestaBD.getUsuarios());
                tvUsuarios.setItems(usuarios);
                break;
        }
    }
    
    

    @FXML
    private void clicBotonModificar(ActionEvent event) {
    }

    @FXML
    private void clicBotonAnadirUsuario(ActionEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }

    @FXML
    private void dobleClicFila(MouseEvent event) {
        if ( event.getClickCount() == 2){
            System.out.println("Id Usuario" + tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario());
            int idUsuario = tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario();
            POJUsuario respuestaBD = DAOUsuario.obtenerUsuarioPorId(idUsuario);
            
        } 
    }
    
    
}
