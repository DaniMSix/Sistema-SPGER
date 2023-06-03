/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOInicioSesion;
import sistema.spger.DAO.DAORol;
import sistema.spger.DAO.DAOInicioSesion;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLPantallaDirectorController implements Initializable {

    int usuarioProfesor;
    POJRolRespuesta respuestaBD;
    POJUsuario usuarioVerificado = new POJUsuario();
    int idDirectorDeTrabajo;
    @FXML
    private Button btnAdminAnteproyecto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.usuarioVerificado = DAOInicioSesion.verificarSesionUsuario("dani-@hotmail.com", "123");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPantallaDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLPantallaDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            idDirectorDeTrabajo = DAORol.obtenerIdDirector(usuarioVerificado.getIdUsuario());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPantallaDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioProfesor = idUsuario;
    }
    @FXML
    private void clicAdminAnteproyecto(ActionEvent event) throws IOException {
        Stage escenarioBase = (Stage) btnAdminAnteproyecto.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLAdministrarAnteproyecto.fxml"));
        try{
            Parent vista = loader.load();
            FXMLAdministrarAnteproyectoController pantallaAdministrar = loader.getController();    
            pantallaAdministrar.prepararRolesUsuario(respuestaBD,this.usuarioVerificado,this.idDirectorDeTrabajo);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setAlwaysOnTop(false);
            escenarioBase.setTitle("Administrar Anteproyecto");
            escenarioBase.show();
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "+
                    "intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }
        public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado){
        this.usuarioVerificado = usuarioLogueado;
        this.respuestaBD=respuestaBD;                
    }

    @FXML
    private void clicAsignarAnteproyecto(ActionEvent event) {
        Stage escenarioBase = (Stage) btnAdminAnteproyecto.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLAsignarAnteproyecto.fxml"));
        try{
            Parent vista = loader.load();
            FXMLAsignarAnteproyectoController pantallaAsignar= loader.getController();    
            pantallaAsignar.prepararRolesUsuario(respuestaBD,this.usuarioVerificado,this.idDirectorDeTrabajo);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setAlwaysOnTop(false);
            escenarioBase.setTitle("Asignar Anteproyecto");
            escenarioBase.show();
        }catch(IOException ex){
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "+
                    "intentelo mas tarde", Alert.AlertType.ERROR);
        }        
    }

}
