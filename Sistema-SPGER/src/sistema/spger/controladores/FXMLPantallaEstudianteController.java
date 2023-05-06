package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLPantallaEstudianteController implements Initializable {

    int usuarioEstudiante;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioEstudiante = idUsuario;
    }
    
}
