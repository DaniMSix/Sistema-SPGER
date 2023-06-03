package sistema.spger.controladores;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileNameExtensionFilter;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.DAO.DAOLGAC;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJLGAC;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import static sistema.spger.utils.Constantes.OPERACION_EXITOSA;
import sistema.spger.utils.Utilidades;
import static sistema.spger.utils.Utilidades.mostrarDialogoSimple;
import static sistema.spger.utils.Utilidades.seleccionarArchivo;

public class FXMLAgregarAnteproyectoController implements Initializable {

    @FXML
    private TextField tfNombreAnteproyecto;
    @FXML
    private ComboBox cbModalidad;
    @FXML
    private ComboBox cbLGAC;
    @FXML
    private Label lbDirector;
    @FXML
    private Button btnSeleccionarArchivo;
    byte[] archivoSelec;
    private POJAnteproyecto anteproyectoEdicion;
    ObservableList<String> listarModalidad;
    POJRolRespuesta respuestaBD;
    POJUsuario usuarioVerificado;
    private int idDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {    

    }
        
    @FXML
    private void clicGuardar(ActionEvent event) throws SQLException, FileNotFoundException {
        try{
        int respuesta=-1;
        POJLGAC lgid = (POJLGAC) cbLGAC.getValue();
        POJAnteproyecto nuevoAnteproyecto=null;
        if(anteproyectoEdicion!=null)
            nuevoAnteproyecto=anteproyectoEdicion;
        else
            nuevoAnteproyecto =new POJAnteproyecto();
        if(tfNombreAnteproyecto.getText().length()<1){
            throw new NullPointerException();
        }  
            nuevoAnteproyecto.setNombreAnteproyecto(tfNombreAnteproyecto.getText());
            nuevoAnteproyecto.setModalidad(cbModalidad.getValue().toString());
            nuevoAnteproyecto.setArchivoAnteproyecto(archivoSelec);
            if((nuevoAnteproyecto.getIdAnteproyecto()>0&&lgid.getIdLGAC()>0)||nuevoAnteproyecto.getIdAnteproyecto()==0)
                nuevoAnteproyecto.setLGAC(lgid.getIdLGAC());
            nuevoAnteproyecto.setIdDirectorDeTrabajo(idDirector);
            nuevoAnteproyecto.setIdEstado(4);
            if(nuevoAnteproyecto.getIdAnteproyecto()==0)
                respuesta= DAOAnteproyecto.guardarAnteproyecto(nuevoAnteproyecto);
            if(nuevoAnteproyecto.getIdAnteproyecto()>0)
                respuesta = DAOAnteproyecto.modificarAnteproyecto(nuevoAnteproyecto);
            if(respuesta==OPERACION_EXITOSA){
                Utilidades.mostrarDialogoSimple("Anteproyecto Guardado", "El anteproyecto se ha guardado con exito", Alert.AlertType.INFORMATION);
                cerrarVentana();
            }
            else if (respuesta== Constantes.ERROR_CONSULTA)
                Utilidades.mostrarDialogoSimple("Error al guardar anteproyecto", "Fallo al guardar anteproyecto", Alert.AlertType.ERROR);
            }catch (NullPointerException ne){
                Utilidades.mostrarDialogoSimple("Campos vacios", "Por favor llene los campos vacios", Alert.AlertType.WARNING);
            }
    }

    @FXML
    private void clicPostular(ActionEvent event) {
        //Cuando se da clic en postular se guarda el anteproyecto y pasa a estado postulado
        //
    }


    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
        
    }
        private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbDirector.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLAdministrarAnteproyecto.fxml"));
        try{
            Parent vista = loader.load();
            FXMLAdministrarAnteproyectoController pantallaAdministrar = loader.getController();    
            pantallaAdministrar.prepararRolesUsuario(respuestaBD,this.usuarioVerificado,this.idDirector);
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

    @FXML
    private void clicSeleccionarArchivo(ActionEvent event) throws FileNotFoundException, IOException {
        try{
        File rutaArch = seleccionarArchivo();
        FileInputStream fis = new FileInputStream(rutaArch);
        this.archivoSelec=new byte[fis.available()];
        btnSeleccionarArchivo.setText(rutaArch.getName());
        }catch(NullPointerException e){
           Utilidades.mostrarDialogoSimple("Error archivo", "No se selecciono ningun archivo", Alert.AlertType.ERROR); 
        }
    }


    @FXML
    private void listarModalidad(Event event) {
        listarModalidad = FXCollections.observableArrayList("Trabajo practico","Trabajo practico educativo","Monografia","Tesina","Tesis","EGEL");
        cbModalidad.setItems(listarModalidad);
    }

    @FXML
    private void listarLGAC(Event event) throws SQLException {
        ArrayList<POJLGAC> LGAC = new ArrayList<>();
        DAOLGAC.obtenerInformacionLGAC().getLgac().forEach(lgac -> {
            LGAC.add(lgac);
    });
        ObservableList<POJLGAC> items = FXCollections.observableArrayList(LGAC);
        cbLGAC.setItems(items);
    }
    public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado,int idDirector){
        this.usuarioVerificado = usuarioLogueado;
        this.respuestaBD=respuestaBD; 
        this.idDirector=idDirector;
        lbDirector.setText(usuarioVerificado.getNombre()+" "+usuarioVerificado.getApellidoPaterno()+" "+usuarioVerificado.getApellidoMaterno());
    } 

    void prepararAnteproyetoEdicion(POJAnteproyecto anteproyecto) {
        this.anteproyectoEdicion=anteproyecto;
        tfNombreAnteproyecto.setText(anteproyectoEdicion.getNombreAnteproyecto());
        cbModalidad.setValue(anteproyectoEdicion.getModalidad());
        btnSeleccionarArchivo.setText(anteproyectoEdicion.getNombreAnteproyecto()+".pdf");
        cbLGAC.setValue(new POJLGAC(anteproyectoEdicion.getIdLGAC(),anteproyectoEdicion.getNombreLGAC()));
        this.archivoSelec=anteproyectoEdicion.getArchivoAnteproyecto();
        
    }
   
}
 