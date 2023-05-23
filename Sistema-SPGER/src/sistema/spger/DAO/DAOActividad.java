package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.utils.Constantes;


public class DAOActividad {
    
    public static int registrarActividad(POJActividad actividadARegistrar) {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null){
            try {
                String nombre = actividadARegistrar.getNombre();
                String descripcion = actividadARegistrar.getDescripcion();
                Date fechaLimiteEntrega = actividadARegistrar.getFechaLimiteEntrega();
                Date fechaCreacion = actividadARegistrar.getFechaCreacion();
                int idEstudiante = actividadARegistrar.getIdEstudiante();
                String consulta = "INSERT INTO actividad (nombre, descripcion, fechaCreacion, fechaLimiteEntrega, Estudiante_idUsuario) " +
                    "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, descripcion);
                prepararSentencia.setDate(3, (java.sql.Date) fechaLimiteEntrega);
                prepararSentencia.setDate(4, (java.sql.Date) fechaCreacion);
                prepararSentencia.setInt(5, idEstudiante);
                prepararSentencia.executeUpdate();
               codigoRespuesta = Constantes.OPERACION_EXITOSA;
                }
            catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }  
        } else {
            codigoRespuesta =Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;
    }

}
