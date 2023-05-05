package sistema.spger.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLPrincipalController implements Initializable {

    @FXML
    private MenuItem mItemProfesor;

    @FXML
    private MenuItem mItemResponsableCA;

    @FXML
    private MenuItem mItemEstudiante;

    @FXML
    private MenuItem mItemDirector;

    @FXML
    private MenuItem mItemAdministrador;

    @FXML
    private MenuBar menuBOpciones;
    
    POJUsuario usuarioActual;
    
    List<POJUsuario> listaRoles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }   
    
    public void mostrarRoles(List<POJUsuario> listaRoles){
        
        desactivarItems();
        
        for (int i=0; i<listaRoles.size(); i++){
            String opcionRol = listaRoles.get(i).getRol();
            System.out.print(opcionRol+ "\n");
            switch(opcionRol){
                case "Administrador":
                    mItemAdministrador.setVisible(true);
                    break;
                case "profesor":
                    mItemProfesor.setVisible(true);
                    break;
                case "Director de trabajo":
                    mItemDirector.setVisible(true);
                    break;
                case "Responsable CA":
                    mItemResponsableCA.setVisible(true);
                    break;
                case "Estudiante":
                    mItemEstudiante.setVisible(true);
            }
        } 
    }
    
    public void prepararRolesUsuario(List<POJUsuario> listaRolesDeUsuario){
        listaRoles = listaRolesDeUsuario;
        mostrarRoles(listaRolesDeUsuario);
    }
    
    public void desactivarItems(){
        mItemAdministrador.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemDirector.setVisible(false);
        mItemResponsableCA.setVisible(false);
        mItemEstudiante.setVisible(false);
    }
    
}
//mouse clic
//stage escenario
//secene es lo que esta adentro
//escena lleva al escenario
//Crear escenario