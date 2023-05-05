package sistema.spger.DAO;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class DAOInicioSesion {
    
    public POJUsuario verificarSesionUsuario(String correoUsuario, String contrasenia) throws SQLException, UnsupportedEncodingException{
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
    
    public List<POJUsuario> obtenerRoles(int idUsuario) throws SQLException{
        ArrayList<POJUsuario> listaRoles = new ArrayList<>();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String consulta = "SELECT descripcion from rol where Usuario_idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(!resultado.next()){
                    throw new SQLException("No hay roles registrados en el sistema");
                } else{
                    do{
                    String descripcion = resultado.getString("descripcion");
                    POJUsuario usuarioRol = new POJUsuario();
                    usuarioRol.setRol(descripcion);
                    listaRoles.add(usuarioRol);
                    }while(resultado.next());
                }
            } catch (SQLException ex) {
                //usuarioRol.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }  
        }
        return listaRoles;
    }
}
