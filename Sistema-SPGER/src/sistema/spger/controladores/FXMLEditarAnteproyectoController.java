/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class FXMLEditarAnteproyectoController implements Initializable {

    @FXML
    private TextField tfNombreAnteproyecto;
    @FXML
    private ComboBox<?> cbModalidad;
    @FXML
    private ComboBox<?> cbLGAC;
    @FXML
    private Label lbDirector;
    @FXML
    private Button btnSeleccionarArchivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
    }

    @FXML
    private void clicPostular(ActionEvent event) {
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    @FXML
    private void listarModalidad(Event event) {
    }

    @FXML
    private void listarLGAC(Event event) {
    }

    @FXML
    private void clicSeleccionarArchivo(ActionEvent event) {
    }
    
}
