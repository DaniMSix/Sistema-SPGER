package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.modelo.POJO.POJUsuarioRespuesta;
import sistema.spger.utils.Constantes;


public class DAOUsuario {
    public static POJUsuario registrarUsuario(POJUsuario usuarioARegistrar) {
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
                usuarioARegistrar.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                }
            catch (SQLException ex) {
                usuarioARegistrar.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }  
        } else {
            usuarioARegistrar.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioARegistrar;
    }
    
    public static POJUsuario obtenerIdDeUsuarioRegistrado(POJUsuario usuarioRegistrado){
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if (conexion != null){
            try{
                String nombre = usuarioRegistrado.getNombre();
                String apellidoPaterno = usuarioRegistrado.getApellidoPaterno();
                String apellidoMaterno = usuarioRegistrado.getApellidoMaterno();
                String consulta = "SELECT idUsuario from Usuario where nombre =? and apellidoPaterno = ? and apellidoMaterno = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, apellidoPaterno);
                prepararSentencia.setString(3, apellidoMaterno);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioRegistrado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    int idUsuario = resultado.getInt("idUsuario");
                    System.out.println("idUsuario DAO"+ idUsuario);
                    usuarioRegistrado.setIdUsuario(idUsuario);
                }
            } catch (SQLException ex) {
                usuarioRegistrado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            usuarioRegistrado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioRegistrado;
    }
    
    public static POJUsuarioRespuesta comprobarInformacionDuplicada(POJUsuario usuarioARegistrar){
        POJUsuarioRespuesta respuestaConsulta = new POJUsuarioRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if (conexion != null){
            try{
                String nombre = usuarioARegistrar.getNombre();
                String apellidoPaterno = usuarioARegistrar.getApellidoPaterno();
                String apellidoMaterno = usuarioARegistrar.getApellidoMaterno();
                String consulta = "SELECT COUNT(*) > 0 AS result FROM usuario WHERE nombre = "
                        + "? AND apellidoPaterno = ? AND apellidoMaterno = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, apellidoPaterno);
                prepararSentencia.setString(3, apellidoMaterno);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaConsulta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    boolean usuarioDuplicado = resultado.getBoolean("result");
                    respuestaConsulta.setUsuarioDuplicado(usuarioDuplicado);
                }
                
                
            } catch (SQLException ex) {
                respuestaConsulta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaConsulta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaConsulta;
    }
    
    

}
