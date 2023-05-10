package sistema.spger.DAO;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class DAOInicioSesion {
    
    public static POJUsuario verificarSesionUsuario(String correoUsuario, String contrasenia) {
        POJUsuario usuarioVerificado = new POJUsuario();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String consulta = "SELECT * FROM usuario WHERE correo = ? and password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, correoUsuario);
                prepararSentencia.setString(2, contrasenia);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    int idUsuario = resultado.getInt("idUsuario");
                    String correo = resultado.getString("correo");
                    String password = resultado.getString("password");
                    String nombre = resultado.getString("nombre");
                    String apellidoPaterno = resultado.getString("apellidoPaterno");
                    String apellidoMaterno = resultado.getString("apellidoMaterno");
                    usuarioVerificado.setIdUsuario(idUsuario);
                    usuarioVerificado.setCorreo(correo);
                    usuarioVerificado.setContrasenia(password);
                    usuarioVerificado.setNombre(nombre);
                    usuarioVerificado.setApellidoPaterno(apellidoPaterno);
                    usuarioVerificado.setApellidoMaterno(apellidoMaterno);
                }
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }  
        }else{
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
    
    public static List<POJRol> obtenerRoles(int idUsuario) {
        ArrayList<POJRol> listaRoles = new ArrayList<>();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String consulta = "SELECT descripcion from rol where Usuario_idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(!resultado.next()){
                   Utilidades.mostrarDialogoSimple("No existen roles", "No hay roles registrados para este "
                           + "usuario", Alert.AlertType.WARNING);
                } else {
                     do{
                    String descripcion = resultado.getString("descripcion");
                    POJRol rolUsuario = new POJRol();
                    rolUsuario.setDescripcion(descripcion);
                    listaRoles.add(rolUsuario);
                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación", 
                        Alert.AlertType.ERROR);
            }
        } else{
            Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, intentelo más tarde", 
                        Alert.AlertType.ERROR);
        }
        return listaRoles;
    }
    
    public static void registrarUsuario(POJUsuario usuarioARegistrar) {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String correo = usuarioARegistrar.getCorreo();
                String password = usuarioARegistrar.getCorreo();
                String nombre = usuarioARegistrar.getNombre();
                String apellidoPaterno = usuarioARegistrar.getApellidoPaterno();
                String apellidoMaterno = usuarioARegistrar.getApellidoMaterno();
                String consulta = "INSERT INTO usuario (correo, password, nombre, apellidoPaterno, apellidoMaterno) VALUES (?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, correo);
                prepararSentencia.setString(2, password);
                prepararSentencia.setString(3, nombre);
                prepararSentencia.setString(4, apellidoPaterno);
                prepararSentencia.setString(5, apellidoMaterno);
                prepararSentencia.executeUpdate();
                                                
                }
            catch (SQLException ex) {
                //usuarioRol.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }  
        }
        
    }
}
