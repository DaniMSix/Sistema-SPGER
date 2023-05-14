package sistema.spger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sistema.spger.DAO.DAOCurso;
import sistema.spger.DAO.DAOProfesor;
import sistema.spger.DAO.DAOUsuario;
import sistema.spger.modelo.POJO.POJCurso;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.modelo.POJO.POJUsuarioRespuesta;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;




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
    @FXML
    private ComboBox<POJUsuario> cbProfesor;
    
    private ObservableList<POJUsuario> profesores;
    private POJCurso cursoARegistrar = new POJCurso();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboBoxProfesor();
    }    
    
    public void llenarComboBoxProfesor(){
        
        profesores = FXCollections.observableArrayList();
        POJUsuarioRespuesta usuarioBD = DAOProfesor.obtenerInformacionProfesores();
        
        switch(usuarioBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                profesores.addAll(usuarioBD.getProfesores());
                cbProfesor.setItems(profesores);
                cbProfesor.getSelectionModel().selectFirst();
                break;
        }
    }

    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        obtenerInformacionTextFields();
    }
    
    public void obtenerInformacionTextFields(){
        String nombreCurso = txfNombreCurso.getText();
        String nrc = txfNrc.getText();
        String descripcion = txAreaDescripcion.getText();
        String bloque = txfBloque.getText();
        String seccion = txfSeccion.getText();
        String periodo = txfPeriodo.getText();
        
        validarTextFields(nombreCurso, nrc, descripcion, bloque, seccion, periodo);
    }
    
    public void validarTextFields(String nombreCurso, String nrc, String descripcion, 
        String bloque, String seccion, String periodo){
        
        boolean datosValidos = true;
        
            if ((nombreCurso.trim().isEmpty()) || (nrc.trim().isEmpty()) || (descripcion.trim().isEmpty())
                    || (bloque.trim().isEmpty()) || (seccion.trim().isEmpty()) || (periodo.trim().isEmpty())) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                        + "con la información necesaria", Alert.AlertType.ERROR);
            }
            
            if ((DAOCurso.comprobarInformacionDuplicada(cursoARegistrar)).isCursoDuplicado()) {
                datosValidos = false;
                Utilidades.mostrarDialogoSimple("Datos duplicados", "Los "
                        + "datos ya se enecuentran registrados en el sistema, por"
                        + " favor ingresen nuevos datos", Alert.AlertType.WARNING);
            }
            
        if(datosValidos){
            registrarInformacionCurso(nombreCurso, nrc, descripcion, bloque, seccion, periodo);
        }               
    }
    
    public void registrarInformacionCurso(String nombreCurso, String nrc, String descripcion, 
            String bloque, String seccion, String periodo){
        
        cursoARegistrar.setNombre(nombreCurso);
        int nrcConvertidoEnEntero = Integer.parseInt(nrc);
        cursoARegistrar.setNrc(nrcConvertidoEnEntero);
        int bloqueConvertidoEnEntero = Integer.parseInt(bloque);
        cursoARegistrar.setDescripcion(descripcion);
        cursoARegistrar.setBloque(bloqueConvertidoEnEntero);
        int seccionConvertidoEnEntero = Integer.parseInt(seccion);
        cursoARegistrar.setSeccion(seccionConvertidoEnEntero);
        cursoARegistrar.setPeriodo(periodo);
        
        int idProfesor = cbProfesor.getValue().getIdUsuario();
        cursoARegistrar.setIdUsuario(idProfesor);
        
        int codigoRespuesta = DAOCurso.registrarCurso(cursoARegistrar);
        
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
                    Utilidades.mostrarDialogoSimple("Información guardada", "El usuario se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
        }
    
    }
    
    @FXML
    private void verificarDatosIngresadosNombreCurso(KeyEvent event) {
    }

    @FXML
    private void verificarDatosIngresadosNrc(KeyEvent event) {
    }

    @FXML
    private void verificarDatosIngresadosDescripcion(KeyEvent event) {
    }

    @FXML
    private void verificarDatosIngresadosBloque(KeyEvent event) {
    }

    @FXML
    private void verificarDatosIngresadosPeriodo(KeyEvent event) {
    }

    @FXML
    private void verificarDatosIngresadosSeccion(KeyEvent event) {
    }
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
}
