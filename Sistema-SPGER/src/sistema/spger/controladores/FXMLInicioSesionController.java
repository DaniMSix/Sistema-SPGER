package sistema.spger.controladores;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOInicioSesion;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) throws SQLException, UnsupportedEncodingException {
        validarCampos();
    }
    
    
    private void validarCampos() throws SQLException, UnsupportedEncodingException{
        String nombreUsuario = tfUsuario.getText();
        String contrasenia = tfContrasenia.getText();
        
        DAOInicioSesion.verificarSesionUsuario(nombreUsuario, contrasenia);
        
        mostrarPantallaPrincipal();
        
        

        
    
    }
    
    private void mostrarPantallaPrincipal(){
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        System.out.println("Éxito2");
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipal.fxml"));
        
        escenarioBase.setTitle("Home");
        escenarioBase.show();
    }
       
}
