package sistema.spger.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sistema.spger.DAO.DAOInicioSesion;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;


public class FXMLAnadirUsuarioController implements Initializable {

    @FXML
    private ComboBox<String> cbNombreRoles;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    POJUsuario usuarioARegistrar = new POJUsuario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarComboBoxNombreRoles();
    }    
    
    public void llenarComboBoxNombreRoles(){
        cbNombreRoles.getItems().addAll(
        "Administrador",
        "Profesor",
        "Director de trabajo",
        "Responsable CA",
        "Estudiante"
        );
    }
    
    public void recuperarInformacionVentana(){
        
    }

    @FXML
    private void clicGuardarInformacionUsuario(ActionEvent event) throws SQLException {
        
        String correo = tfCorreo.getText();
        String contrasenia = tfContrasenia.getText();
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        usuarioARegistrar.setCorreo(correo);
        usuarioARegistrar.setContrasenia(contrasenia);
        usuarioARegistrar.setNombre(nombre);
        usuarioARegistrar.setApellidoPaterno(apellidoPaterno);
        usuarioARegistrar.setApellidoMaterno(apellidoMaterno);
        
        DAOInicioSesion.registrarUsuario(usuarioARegistrar);
        Utilidades.mostrarDialogoSimple("Información registrada", 
                "La información se ha registrado correctamente", Alert.AlertType.INFORMATION);
        
        
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }
    
    
}
