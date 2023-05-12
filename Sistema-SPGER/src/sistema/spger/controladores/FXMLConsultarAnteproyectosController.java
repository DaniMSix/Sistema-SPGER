package sistema.spger.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.modelo.POJO.POJAnteproyecto;


public class FXMLConsultarAnteproyectosController implements Initializable {

    @FXML
    private TableView<POJAnteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colDocumento;
    @FXML
    private TableColumn colLGAC;
    @FXML
    private TableColumn colNombreDirector;
    @FXML
    private TableColumn colEstadoAnteproyecto;
    ObservableList<POJAnteproyecto> listaAnteproyectos;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosTabla();
    } 
    
        private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("boton"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("LGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("estado"));
    }
        private void cargarDatosTabla(){
        try {
            listaAnteproyectos = FXCollections.observableArrayList();
            ArrayList<POJAnteproyecto> anteproyectosBD = DAOAnteproyecto.obtenerInformacionAnteproyecto().getAnteproyectos();
            listaAnteproyectos.addAll(anteproyectosBD);
            tvAnteproyectos.setItems(listaAnteproyectos);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    
}
