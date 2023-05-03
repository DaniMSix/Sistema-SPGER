package sistema.spger.DAO;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class DAOInicioSesion {
    
    public static POJUsuario verificarSesionUsuario(String nombreUsuario, String contrasenia) throws SQLException, UnsupportedEncodingException{
        POJUsuario usuarioVerificado = new POJUsuario();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            
            try {
                String consulta = "SELECT * FROM usuario where nombreusuario =? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1,nombreUsuario);
                prepararSentencia.setString(2, contrasenia);
                PreparedStatement statement = conexion.prepareStatement(consulta);
                statement.setString(1, nombreUsuario);
                statement.setString(2, Utilidades.encriptarContraseñaSHA512(contrasenia));
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombreUsuario(resultado.getString("nombreUsuario"));
                    usuarioVerificado.setContrasenia(resultado.getString("contrasenia"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setCorreo(resultado.getString("correo"));
                }
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }  
        }else{
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
    
}
