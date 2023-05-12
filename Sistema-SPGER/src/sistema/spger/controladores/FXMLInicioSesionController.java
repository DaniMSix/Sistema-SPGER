package sistema.spger.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOInicioSesion;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfCorreo;
    @FXML
    private AnchorPane anchorPInicioSesion;
    @FXML
    private PasswordField pfContrasenia;
    
    POJUsuario usuarioActivo = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) throws SQLException, UnsupportedEncodingException, IOException {
        validarCampos();
    }
    
    
    private void validarCampos() throws SQLException, UnsupportedEncodingException, IOException{
        String correo = tfCorreo.getText();
        String contrasenia = pfContrasenia.getText();      
        DAOInicioSesion usuarioDao = new DAOInicioSesion();
        POJUsuario usuario = usuarioDao.verificarSesionUsuario(correo, contrasenia);
        List<POJUsuario> listaRoles = new ArrayList<>();
        int idUsuario = usuario.getIdUsuario();
        listaRoles = usuarioDao.obtenerRoles(idUsuario);
        if(correo.isEmpty() && contrasenia.isEmpty() ){
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Hay campos"
                    + " vacíos coloque la información correspondiente en todos", Alert.AlertType.ERROR);
        } else{
            mostrarPantallaPrincipal(listaRoles, usuarioActivo);
        }
    }
    
   
    
    public void mostrarPantallaPrincipal(List<POJUsuario> listaRolesDeUsuario, POJUsuario usuarioActivo) throws IOException{
       
        Stage escenarioBase = (Stage) tfCorreo.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaPrincipal.fxml"));
        try {
            Parent vista = loader.load();
            FXMLPantallaPrincipalController pantallaPrincipal = loader.getController();
            //pantallaPrincipal.prepararRolesUsuario(listaRolesDeUsuario, usuarioActivo);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);       
            escenarioBase.setAlwaysOnTop(false);
            escenarioBase.show();
            escenarioBase.setTitle("SSPGER");
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }
    
    
    
    // <a href="https://storyset.com/user">User illustrations by Storyset</a>
       
}
