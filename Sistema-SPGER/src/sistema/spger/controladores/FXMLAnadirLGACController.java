package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistema.spger.DAO.DAOLgac;
import sistema.spger.DAO.DAOUsuario;
import sistema.spger.modelo.POJO.POJLgac;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLAnadirLGACController implements Initializable {

    @FXML
    private TextField txfNombre;
    @FXML
    private TextArea txAreaDescripcion;
    
    private POJLgac lgacARegistrar = new POJLgac();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        obtenerInformacionIngresada();
    }
    
    public void obtenerInformacionIngresada(){
        String nombre = txfNombre.getText();
        String descripcion = txAreaDescripcion.getText();
        validarTextFields(nombre, descripcion);
    }
    
    public void validarTextFields(String nombre, String descripcion){
        
        boolean datosValidos = true;
        
            System.out.println("Validar");
            if ((nombre.trim().isEmpty()) || (descripcion.trim().isEmpty()) ) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                        + "con la información necesaria", Alert.AlertType.ERROR);
            }
            
            if ((DAOLgac.comprobarInformacionDuplicada(lgacARegistrar).isLgacDuplicado())) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Datos duplicados", "Los "
                        + "datos ya se enecuentran registrados en el sistema, por"
                        + " favor ingresen nuevos datos", Alert.AlertType.WARNING);
            } 
        if(datosValidos){
            registrarInformacionLgac(nombre, descripcion);
        }
        
    }
    
    public void registrarInformacionLgac(String nombre, String descripcion){
        
        lgacARegistrar.setNombre(nombre);
        lgacARegistrar.setDescripcion(descripcion);
        
        int codigoRespuesta = DAOLgac.registrarLgac(lgacARegistrar);
        
        switch (codigoRespuesta) {
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
                    Utilidades.mostrarDialogoSimple("Información guardada", "El LGAC se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
        }
        
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
