package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;

public class FXMLCapturarInformacionAlumnoController implements Initializable {

    private POJRolRespuesta respuestaBD;
    private POJUsuario usuarioVerificado;
    private int idDirectorDeTrabajo;
    private POJAnteproyecto anteproyectoAsignar;
    @FXML
    private ListView<?> lvEstudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioVerificado, int idDirectorDeTrabajo) {
        this.respuestaBD=respuestaBD;
        this.usuarioVerificado=usuarioVerificado;
        this.idDirectorDeTrabajo=idDirectorDeTrabajo;
    }

    void prepararAnteproyeto(POJAnteproyecto anteproyectoAsignar) {
        this.anteproyectoAsignar=anteproyectoAsignar;
    }

    @FXML
    private void clicAsignar(ActionEvent event) {
    }
    
}
