/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLAnadirCursoController implements Initializable {

    @FXML
    private TextField txfNombreCurso;
    @FXML
    private TextField txfNrc;
    @FXML
    private TextArea txAreaDescripcion;
    @FXML
    private Button clicBotonGuardar;
    @FXML
    private TextField txfBloque;
    @FXML
    private TextField txfPeriodo;
    @FXML
    private TextField txfSeccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
