package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sistema.spger.DAO.DAORol;
import sistema.spger.DAO.DAOUsuario;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;


public class FXMLAnadirUsuarioController implements Initializable {

    private ComboBox<String> cbNombreRoles;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    POJUsuario usuarioARegistrar = new POJUsuario();
    POJRol rolARegistrar = new POJRol();
            
    @FXML
    private CheckBox cbxAdministrador;
    @FXML
    private CheckBox cbxProfesor;
    @FXML
    private CheckBox cbxDirectorDeTrabajo;
    @FXML
    private CheckBox cbxResponsableCA;
    @FXML
    private CheckBox cbxEstudiante;
    
    String EXPRESION_COMPROBAR_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    String EXPRESION_CARACTERES_ESPECIALES = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}‘；：”“’。，、0-9]";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       tfCorreo.setText("");
       tfContrasenia.setText("");
       tfNombre.setText("");
       tfApellidoPaterno.setText("");
       tfApellidoMaterno.setText("");
    }    
    
    @FXML
    private void clicGuardarInformacionUsuario(ActionEvent event) {
        obtenerInformacionTextFields();
    }
    
    public void obtenerInformacionTextFields(){
        String correo = tfCorreo.getText();
        String contrasenia = tfContrasenia.getText();
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        validarTextFields(correo, contrasenia, nombre, apellidoPaterno, apellidoMaterno);
    }
    
    public void validarTextFields(String correo, String contrasenia, String nombre, 
        String apellidoPaterno, String apellidoMaterno){
        
        boolean datosValidos = true;
        
            System.out.println("Validar");
            if ((correo.trim().isEmpty()) || (contrasenia.trim().isEmpty()) || (nombre.trim().isEmpty())
                    || (apellidoPaterno.trim().isEmpty()) || (apellidoMaterno.trim().isEmpty())) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                        + "con la información necesaria", Alert.AlertType.ERROR);
            }
            
            if (!(correo.matches(EXPRESION_COMPROBAR_EMAIL))) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Correo inválido", "Por favor ingrese un "
                        + "correo válido", Alert.AlertType.ERROR);
            }
            
            if (!(comprobarSeleccionRol())) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Sin seleccion", "Seleccione"
                        + " los roles necesarios para el usuario", Alert.AlertType.WARNING);
                
            }
            
            System.out.println("Nombre " + usuarioARegistrar.getNombre());
            
            if ((DAOUsuario.comprobarInformacionDuplicada(usuarioARegistrar).getUsuarioDuplicado())) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Datos duplicados", "Los "
                        + "datos ya se enecuentran registrados en el sistema, por"
                        + " favor ingresen nuevos datos", Alert.AlertType.WARNING);
            } 
        if(datosValidos){
            registrarInformacionUsuario(correo, contrasenia, nombre, apellidoPaterno, apellidoMaterno);
        }
        
    }
    
    public void registrarInformacionUsuario(String correo, String contrasenia, String nombre,
        String apellidoPaterno, String apellidoMaterno) {

        POJUsuario usuarioRespuesta;

        usuarioARegistrar.setCorreo(correo);
        usuarioARegistrar.setContrasenia(contrasenia);
        usuarioARegistrar.setNombre(nombre);
        usuarioARegistrar.setApellidoPaterno(apellidoPaterno);
        usuarioARegistrar.setApellidoMaterno(apellidoMaterno);
        
        usuarioRespuesta = DAOUsuario.registrarUsuario(usuarioARegistrar);
        obtenerIdUsuario(usuarioARegistrar);
        
        switch (usuarioRespuesta.getCodigoRespuesta()) {
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
                if (usuarioRespuesta.getIdUsuario() > 0) {
                    Utilidades.mostrarDialogoSimple("Información guardada", "El usuario se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
                }
        }

    }
    
    
    public void obtenerIdUsuario(POJUsuario usuarioARegistrar){
        POJUsuario rolUsuario;
        rolUsuario = DAOUsuario.obtenerIdDeUsuarioRegistrado(usuarioARegistrar);
        
        rolUsuario = DAOUsuario.obtenerIdDeUsuarioRegistrado(usuarioARegistrar);
        int idUsuario = rolUsuario.getIdUsuario();
        int codigoRespuesta = rolUsuario.getCodigoRespuesta();
        
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Por el momento no hay conexión, intentelo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud de obtención del rol",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                registrarRol(idUsuario);
        }
    }
    
    public void registrarRol(int idUsuario){
        
        String nombreRol;
        int codigoRespuesta;
        
        if(cbxAdministrador.isSelected()){
            nombreRol = cbxAdministrador.getText();
            rolARegistrar.setDescripcion(nombreRol);
            rolARegistrar.setIdUsuario(idUsuario);
            codigoRespuesta = DAORol.registrarRolUsuario(rolARegistrar).getCodigoRespuesta();
            comprobarRolGuardado(codigoRespuesta);
        }
        
        if(cbxProfesor.isSelected()){
            nombreRol = cbxProfesor.getText();
            rolARegistrar.setDescripcion(nombreRol);
            rolARegistrar.setIdUsuario(idUsuario);
            codigoRespuesta = DAORol.registrarRolUsuario(rolARegistrar).getCodigoRespuesta();
            comprobarRolGuardado(codigoRespuesta);
        }
            
        if(cbxDirectorDeTrabajo.isSelected()){
            nombreRol = cbxDirectorDeTrabajo.getText();
            rolARegistrar.setDescripcion(nombreRol);
            rolARegistrar.setIdUsuario(idUsuario);
            codigoRespuesta = DAORol.registrarRolUsuario(rolARegistrar).getCodigoRespuesta();
            comprobarRolGuardado(codigoRespuesta);
        }
        
        if(cbxResponsableCA.isSelected()){
            nombreRol = cbxResponsableCA.getText();
            rolARegistrar.setDescripcion(nombreRol);
            rolARegistrar.setIdUsuario(idUsuario);
            codigoRespuesta = DAORol.registrarRolUsuario(rolARegistrar).getCodigoRespuesta();
            comprobarRolGuardado(codigoRespuesta);
        }
        
        if(cbxEstudiante.isSelected()){
            nombreRol = cbxEstudiante.getText();
            rolARegistrar.setDescripcion(nombreRol);
            rolARegistrar.setIdUsuario(idUsuario);
            codigoRespuesta = DAORol.registrarRolUsuario(rolARegistrar).getCodigoRespuesta();
            comprobarRolGuardado(codigoRespuesta);
        }
        
    }
    
    public boolean comprobarSeleccionRol(){
        boolean seleccion = false;
        
        if((cbxAdministrador.isSelected()) || (cbxProfesor.isSelected()) 
                || (cbxDirectorDeTrabajo.isSelected()) || (cbxResponsableCA.isSelected())
                || (cbxEstudiante.isSelected())){
            seleccion = true;
        }
        return seleccion;
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }
    
    @FXML
    private void limitarCaracteresIngresadosCorreo(KeyEvent event) {
        if(tfCorreo.getText().length() >=60){
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresIngresadosContrasenia(KeyEvent event) {
        if(tfContrasenia.getText().length() >=30){
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresIngresadosNombre(KeyEvent event) {
        if(tfNombre.getText().length() >=30){
            event.consume();
        }
        String textoIngresado = event.getCharacter();
        if (textoIngresado.matches(EXPRESION_CARACTERES_ESPECIALES)){
            Utilidades.mostrarDialogoSimple("Caracteres no permitidos", 
                    "Pof favor no ingrese números ni caracteres especiales "
                            + "en este campo", Alert.AlertType.WARNING);
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresIngresadosApellidoPaterno(KeyEvent event) {
        if(tfApellidoPaterno.getText().length() >=40){
            event.consume();
        }
        
        String textoIngresado = event.getCharacter();
        if (textoIngresado.matches(EXPRESION_CARACTERES_ESPECIALES)){
            Utilidades.mostrarDialogoSimple("Caracteres no permitidos", 
                    "Pof favor no ingrese números ni caracteres especiales "
                            + "en este campo", Alert.AlertType.WARNING);
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresIngresadosApellidoMaterno(KeyEvent event) {
        if(tfApellidoMaterno.getText().length() >=40){
            event.consume();
        }
        
        String textoIngresado = event.getCharacter();
        if (textoIngresado.matches(EXPRESION_CARACTERES_ESPECIALES)){
            Utilidades.mostrarDialogoSimple("Caracteres no permitidos", 
                    "Pof favor no ingrese números ni caracteres especiales "
                            + "en este campo", Alert.AlertType.WARNING);
            event.consume();
        }
    }
    
    public void comprobarRolGuardado(int codigoRespuesta){
         switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Por el momento no hay conexión, intentelo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud de obtención del rol",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Rol guardado con éxito",
                        "Se ha guardado correctamente el rol del usuario",
                        Alert.AlertType.INFORMATION);
        }
    }
    
}
