package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;
import sistema.spger.utils.Utilidades;

public class FXMLPantallaResponsableCAController implements Initializable {

    int usuarioResponsableCA;
    @FXML
    private Button btnConsultarAnteproyecto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("se inicio ventana");
    }    
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioResponsableCA = idUsuario;
    }


    

    @FXML
    private void clicConsultarAnteproyectos(ActionEvent event) {
        Stage escenarioBase = (Stage) btnConsultarAnteproyecto.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLConsultarAnteproyectos.fxml"));
        try{
            Parent vista = loader.load();
            FXMLConsultarAnteproyectosController pantallaConsultar = loader.getController();    
            //pantallaConsultar.prepararRolesUsuario(respuestaBD,this.usuarioVerificado,this.idDirectorDeTrabajo);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setAlwaysOnTop(false);
            escenarioBase.setTitle("Consultar Anteproyecto");
            escenarioBase.show();
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "+
                    "intentelo mas tarde", Alert.AlertType.ERROR);
        }        
    }
    
}
