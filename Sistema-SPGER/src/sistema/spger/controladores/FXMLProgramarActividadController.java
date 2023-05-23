/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLProgramarActividadController implements Initializable {

    @FXML
    private TextField txfNombre;
    @FXML
    private TextArea txAreaDescripcion;
    @FXML
    private DatePicker dpFechaEntrega;
    POJUsuario estudianteActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void recibirInformacionEstudiante(POJUsuario usuarioLogueado){
        estudianteActual = usuarioLogueado;
    }

    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        obtenerInformacionIngresada();
    }
        
    public void obtenerInformacionIngresada(){
        String nombre = txfNombre.getText();
        String descripcion = txAreaDescripcion.getText();
        validarInformacion(nombre, descripcion, dpFechaEntrega);
    }
    
    
    public void validarInformacion(String nombre, String descripcion, DatePicker fechaLimiteEntrega) {
        
        boolean datosValidos = true;
        
        System.out.println("Validar");
        
        if(fechaLimiteEntrega.getValue() == null){
            System.out.println("Validar fecha después");
            Utilidades.mostrarDialogoSimple("Fecha incorrecta", "Por favor seleccione una"
                    + " fecha", Alert.AlertType.ERROR);
        } else {
            if(fechaLimiteEntrega.getValue().isBefore(LocalDate.now())){
            
            Utilidades.mostrarDialogoSimple("Fecha incorrecta", "Por favor seleccione una"
                    + " fecha después a la actual", Alert.AlertType.ERROR);
            }
        }
                
        if ((nombre.trim().isEmpty()) || (descripcion.trim().isEmpty())) {
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                    + "con la información necesaria", Alert.AlertType.ERROR);
        }
        
        if(datosValidos){
            registrarInformacionValidada(nombre, descripcion, fechaLimiteEntrega);
        }
    }
    
    public void registrarInformacionValidada(String nombre, String descripcion, DatePicker fechaLimiteEntrega){
        
        POJActividad actividadARegistrar = new POJActividad();
        actividadARegistrar.setNombre(nombre);
        actividadARegistrar.setDescripcion(descripcion);
        actividadARegistrar.setFechaLimiteEntrega(Date.valueOf(fechaLimiteEntrega.getValue()));
        actividadARegistrar.setFechaCreacion(Date.valueOf(LocalDate.now()));
        actividadARegistrar.setIdEstudiante(44);
        
        int respuesta = DAOActividad.registrarActividad(actividadARegistrar);
        switch (respuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Por el momento no hay conexión, intentelo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Información guardada", "El usuario se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
