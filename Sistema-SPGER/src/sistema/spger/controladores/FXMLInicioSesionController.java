package sistema.spger.controladores;

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
    private void clicIniciarSesion(ActionEvent event) throws SQLException {
        validarCampos();
    }
    
    private void validarCredencialesUsuario(String nombreUsuario, String contrasenia) throws SQLException{
        POJUsuario usuarioRespuesta = DAOInicioSesion.verificarSesionUsuario(nombreUsuario, contrasenia);
             
            switch (usuarioRespuesta.getCodigoRespuesta()){
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la solicitud", "Por el momento no se puede procesar la solicitud", Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error conexión", "Por el momento no hay conexión con la BD", Alert.AlertType.ERROR);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    if (usuarioRespuesta.getIdUsuario() > 0){
                        
                        Utilidades.mostrarDialogoSimple("Usuario verificado", 
                            "Bienvenido "+ usuarioRespuesta.toString()+" al sistema...", Alert.AlertType.INFORMATION);
                    } else{
                        Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                            "El usuario y/o contraseñas son incorrectas", Alert.AlertType.WARNING);
                    }
                    break;
                default:
                        Utilidades.mostrarDialogoSimple("Error de petición", 
                            "El sistema no esta disponible por el momento", Alert.AlertType.ERROR);
               
            }   
    }
    
    
    private void validarCampos() throws SQLException{
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
