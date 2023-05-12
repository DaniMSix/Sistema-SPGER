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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.DAO.DAOLGAC;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJLGAC;
import sistema.spger.utils.Constantes;
import static sistema.spger.utils.Constantes.OPERACION_EXITOSA;
import sistema.spger.utils.Utilidades;
import static sistema.spger.utils.Utilidades.mostrarDialogoSimple;
import static sistema.spger.utils.Utilidades.seleccionarArchivo;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<POJLGAC> LGAC = new ArrayList<>();
        try {
            DAOLGAC.obtenerInformacionLGAC().getLgac().forEach(lgac -> {
                    LGAC.add(lgac);
            });
        } catch(SQLException ex) {
            Logger.getLogger(FXMLAgregarAnteproyectoController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
    @FXML
    private void clicGuardar(ActionEvent event) throws SQLException, FileNotFoundException {
        //Guarda en la base de datos la instancia de anteproyecto
        POJLGAC lgid = (POJLGAC) cbLGAC.getValue();
        POJAnteproyecto nuevoAnteproyecto = new POJAnteproyecto();
        nuevoAnteproyecto.setNombreAnteproyecto(tfNombreAnteproyecto.getText());
        nuevoAnteproyecto.setModalidad(cbModalidad.getValue().toString());
        nuevoAnteproyecto.setArchivoAnteproyecto(archivoSelec);
        nuevoAnteproyecto.setLGAC(lgid.getIdLGAC());
        int respuesta= DAOAnteproyecto.guardarAnteproyecto(nuevoAnteproyecto);
        System.out.println("respuesta"+respuesta);
        if(respuesta==OPERACION_EXITOSA)
            Utilidades.mostrarDialogoSimple("Anteproyecto Guardado", "El anteproyecto se ha guardado con exito", Alert.AlertType.INFORMATION);
        else if (respuesta== Constantes.ERROR_CONSULTA)
            Utilidades.mostrarDialogoSimple("Error al guardar anteproyecto", "Fallo al guardar anteproyecto", Alert.AlertType.ERROR);
    }

    @FXML
    private void clicPostular(ActionEvent event) {
    }


    @FXML
    private void clicCancelar(ActionEvent event) {
        
    }

    @FXML
    private void clicSeleccionarArchivo(ActionEvent event) throws FileNotFoundException, IOException {
        File rutaArch = seleccionarArchivo();
        FileInputStream fis = new FileInputStream(rutaArch);
        this.archivoSelec=new byte[fis.available()];
        btnSeleccionarArchivo.setText(rutaArch.getName());
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
    public void inicializarValores(POJAnteproyecto  anteproyecto){
        this.anteproyectoEdicion = anteproyecto;
        if(anteproyecto !=null)
            cargarInformacionAnteproyectoEdicion();
    }

    private void cargarInformacionAnteproyectoEdicion() {
        tfNombreAnteproyecto.setText(anteproyectoEdicion.getNombreAnteproyecto());
        cbLGAC.setValue(anteproyectoEdicion.getLGAC());
        btnSeleccionarArchivo.setText(anteproyectoEdicion.getNombreAnteproyecto()+".pdf");
        cbModalidad.setValue(anteproyectoEdicion.getLGAC());
        btnSeleccionarArchivo.setText(anteproyectoEdicion.getNombreAnteproyecto());
        lbDirector.setText(anteproyectoEdicion.getNombreDirectorDeTrabajo());
    }
    
    
}
 