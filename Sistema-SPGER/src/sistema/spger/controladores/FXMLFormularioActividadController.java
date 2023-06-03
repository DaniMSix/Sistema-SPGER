/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLFormularioActividadController implements Initializable {

    @FXML
    private TextField txfNombre;
    @FXML
    private TextArea txAreaDescripcion;
    @FXML
    private DatePicker dpFechaEntrega;
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dpFechaCreacion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    String tipoBoton;
    
    POJActividad actividadInformacion = new  POJActividad();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dpFechaEntrega.setDayCellFactory(getDayCellFactory());
        //dpFechaCreacion.setValue(LocalDate.now());
        //dpFechaCreacion.setEditable(false);
    }    
    
    public int inicializarInformacionFormulario(String tipoClicBoton, POJActividad actividadEdicion){

        actividadInformacion =  actividadEdicion;
        tipoBoton = tipoClicBoton;
       
       switch(tipoClicBoton){
            case "Ver detalle":
                lbTitulo.setText("Detalle actividad");
                cargarInformacionDetalle(actividadEdicion);
                break;
            case "Modificar":
                lbTitulo.setText("Modificar actividad");
                asignarInformacionCampos(actividadInformacion);
                dpFechaCreacion.setEditable(false);
                break;
            case "Registrar":
                dpFechaCreacion.setEditable(false);
                dpFechaCreacion.setValue(LocalDate.now());
                
                break;
       }
       return actividadInformacion.getIdEstudiante();
    }
    
    
    public int obtenerIdEstudiante(POJActividad actividadInformacion){
       int idEstudiante = actividadInformacion.getIdEstudiante();
       return idEstudiante;
    }
    
    
    private void cargarInformacionDetalle(POJActividad informacionActividad){
        asignarInformacionCampos(informacionActividad);
        desactivarCampos();
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }
    
    public void desactivarCampos(){
        txfNombre.setEditable(false);
        txAreaDescripcion.setEditable(false);
        dpFechaCreacion.setEditable(false);
        dpFechaEntrega.setEditable(false);
    }

    public void asignarInformacionCampos(POJActividad informacionActividad){
        txfNombre.setText(informacionActividad.getNombre());
        txAreaDescripcion.setText(informacionActividad.getDescripcion());
        dpFechaEntrega.setValue(LocalDate.parse(informacionActividad.getFechaLimiteEntrega()));
        dpFechaCreacion.setValue(LocalDate.parse(informacionActividad.getFechaCreacion()));
        
    }
    
    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        if(tipoBoton.equals("Modificar")){
            POJActividad actividadAModificar = obtenerInformacionIngresada();
            actividadAModificar.setIdActividad(actividadInformacion.getIdActividad());
                int respuestaBD = DAOActividad.modificarActividad(actividadAModificar);
                switch (respuestaBD) {
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
                    Utilidades.mostrarDialogoSimple("Información modificada correctamente", "El usuario se ha modificado correctamente",
                            Alert.AlertType.INFORMATION);
            }
        } 
        
        if(tipoBoton.equals("Registrar")){
            System.err.println("id estudiante registrar " + actividadInformacion.getIdEstudiante());
            System.err.println("Entro");
            POJActividad actividadARegistrar = obtenerInformacionIngresada();
            actividadARegistrar.setIdEstudiante(actividadInformacion.getIdEstudiante());
            System.out.println("id estudiante " +actividadARegistrar.getIdEstudiante());
            actividadARegistrar.setEstado("Sin entregar");
                int respuestaBD = DAOActividad.registrarActividad(actividadARegistrar);
                switch (respuestaBD) {
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
                    Utilidades.mostrarDialogoSimple("Información registrada correctamente", "El usuario se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
        }
        }
    }
    
    
        
    public POJActividad obtenerInformacionIngresada(){
        POJActividad actividadInformacionIngresada = new POJActividad();
        String nombre = txfNombre.getText();
        String descripcion = txAreaDescripcion.getText();
        LocalDate fechaNacimiento = dpFechaEntrega.getValue();
        actividadInformacionIngresada.setNombre(nombre);
        actividadInformacionIngresada.setDescripcion(descripcion);
        actividadInformacionIngresada.setFechaLimiteEntrega(fechaNacimiento.toString());
        actividadInformacionIngresada.setFechaCreacion(LocalDate.now().toString());
        return actividadInformacionIngresada;
    }
    
    
    public boolean validarInformacion(POJActividad actividadInformacionIngresada) {
        
        boolean datosValidos = true;
        
        System.out.println("Validar");
        
        if(actividadInformacionIngresada.getFechaLimiteEntrega() == null){
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Fecha incorrecta", "Por favor seleccione una"
                    + " fecha", Alert.AlertType.ERROR);
        }
        
        if ((actividadInformacionIngresada.getNombre().isEmpty()) || (actividadInformacionIngresada.getDescripcion().isEmpty())) {
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                    + "con la información necesaria", Alert.AlertType.ERROR);
        }
        
        if(datosValidos){
            datosValidos = true;
        }
        
        
        return datosValidos;
    }
    
    
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if(date.isBefore(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }
    
    /*
    public void registrarInformacionValidada(String nombre, String descripcion, DatePicker fechaLimiteEntrega){
        
        POJActividad actividadARegistrar = new POJActividad();
        actividadARegistrar.setNombre(nombre);
        actividadARegistrar.setDescripcion(descripcion);
        actividadARegistrar.setFechaLimiteEntrega(fechaLimiteEntrega.getValue().toString());
        String fechaActual = LocalDate.now().toString();
        actividadARegistrar.setFechaCreacion(fechaActual);
        actividadARegistrar.setEstado("Sin entregar");
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
    */
    
}
