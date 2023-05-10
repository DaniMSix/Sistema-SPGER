package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLPantallaPrincipalController implements Initializable {

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
    
    List<POJRol> listaRoles;
    
    POJUsuario usuarioLogueado = null;
    @FXML
    private AnchorPane anchoPnPrincipal;
    @FXML
    private Label lbNombreUsuario;
    
    private POJUsuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       desactivarItems();
    }   
    
    public void mostrarRoles(List<POJRol> listaRoles){
        
        
        
        for (int indice=0; indice<listaRoles.size(); indice++){
            String opcionRol = listaRoles.get(indice).getDescripcion();
            switch(opcionRol){
                case "Administrador":
                    mItemAdministrador.setVisible(true);
                    break;
                case "Profesor":
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
    
    public void prepararRolesUsuario(List<POJRol> listaRolesDeUsuario, POJUsuario usuarioLogueado){
        usuarioActual = usuarioLogueado;
        listaRoles = listaRolesDeUsuario;
        
        mostrarRoles(listaRoles);
        lbNombreUsuario.setText(usuarioLogueado.getNombre());
        
        
    }
    
    public void desactivarItems(){
        mItemAdministrador.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemDirector.setVisible(false);
        mItemResponsableCA.setVisible(false);
        mItemEstudiante.setVisible(false);
    }
    
    @FXML
    private void clicMItemAdministrador(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaAdministrador.fxml"));
            Parent vista = loader.load();
            FXMLPantallaAdministradorController administradorControler = loader.getController();
            administradorControler.recibirInformaciónAdministrador(usuarioLogueado);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Administrador");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }
        
    @FXML
    private void clicMItemDirector(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaDirector.fxml"));
            Parent vista = loader.load();
            FXMLPantallaDirectorController pantallaDirector = loader.getController();
            //pantallaDirector.prepararRolesUsuario(usuarioActivo);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    private void clicMItemResponsableCA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLResponsableCA.fxml"));
            Parent vista = loader.load();
            FXMLPantallaResponsableCAController pantallaResponsableCA = loader.getController();
            //pantallaResponsableCA.prepararRolesUsuario(idUsuarioActual);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    private void clicMItemEstudiante(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLResponsableCA.fxml"));
            Parent vista = loader.load();
            FXMLPantallaEstudianteController pantallaEstudiante = loader.getController();
            //pantallaEstudiante.prepararRolesUsuario(idUsuarioActual);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    private void clicMItemProfesor(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaProfesor.fxml"));
            Parent vista = loader.load();
            FXMLPantallaProfesorController pantallaProfesor = loader.getController();
            //pantallaProfesor.prepararRolesUsuario(idUsuarioActual);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }
}

    
// <a href="https://storyset.com/online">Online illustrations by Storyset</a>

//mouse clic
//stage escenario
//secene es lo que esta adentro
//escena lleva al escenario
//Crear escenario