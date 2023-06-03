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
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;


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
    private POJUsuario usuarioVerificado;
    private POJRolRespuesta respuestaBD;
    private int idDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("se inicio ventana consultar");
        configurarTabla();
        System.out.println("se configuro la tabla");
        cargarDatosTabla();
        System.out.println("se cargaron los datos en la tabla");
    } 
    
        private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colDocumento.setCellValueFactory(new PropertyValueFactory("boton"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("nombreLGAC"));  
        colNombreDirector.setCellValueFactory(new PropertyValueFactory("nombreDirectorDeTrabajo"));
        colEstadoAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreEstado"));
    }
    private void cargarDatosTabla(){
        try {
            listaAnteproyectos = FXCollections.observableArrayList();
            System.out.println("Este es el id Del director "+this.idDirector);            
            ArrayList<POJAnteproyecto> anteproyectosBD = DAOAnteproyecto.obtenerInfoAnteproyectoPorDirector(this.idDirector).getAnteproyectos();
            listaAnteproyectos.addAll(anteproyectosBD);
            tvAnteproyectos.setItems(listaAnteproyectos);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado,int idDirectorDeTrabajo){
        this.usuarioVerificado = usuarioLogueado;
        this.respuestaBD=respuestaBD;
        this.idDirector= idDirectorDeTrabajo;
    } 
    
}
