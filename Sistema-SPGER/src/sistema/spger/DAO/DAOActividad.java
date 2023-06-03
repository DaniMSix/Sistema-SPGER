package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadRespuesta;
import sistema.spger.utils.Constantes;

public class DAOActividad {

    public static int registrarActividad(POJActividad actividadARegistrar) {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO actividad (nombre, descripcion, fechaCreacion, fechaLimiteEntrega, estado, Estudiante_idUsuario) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividadARegistrar.getNombre());
                prepararSentencia.setString(2, actividadARegistrar.getDescripcion());
                prepararSentencia.setString(3, actividadARegistrar.getFechaCreacion());
                prepararSentencia.setString(4, actividadARegistrar.getFechaLimiteEntrega());
                prepararSentencia.setString(5, actividadARegistrar.getEstado());
                prepararSentencia.setInt(6, actividadARegistrar.getIdEstudiante());
                int filasInsertadas = prepararSentencia.executeUpdate();
                if (filasInsertadas > 0) {
                    codigoRespuesta = Constantes.OPERACION_EXITOSA;
                } else {
                    codigoRespuesta = Constantes.ERROR_CONSULTA;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;

    }

    public static POJActividadRespuesta obtenerActividadesProgramadas(int idEstudiante) {
        POJActividadRespuesta respuesta = new POJActividadRespuesta();
        ArrayList<POJActividad> actividadConsulta = new ArrayList();

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT idActividad, nombre, descripcion, fechaCreacion,  fechaLimiteEntrega, estado, Estudiante_idUsuario FROM actividad WHERE Estudiante_idUsuario = ?;";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                while (resultado.next()) {
                    POJActividad actividad = new POJActividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    actividad.setEstado(resultado.getString("estado"));
                    actividad.setIdEstudiante(resultado.getInt("Estudiante_idUsuario"));
                    actividadConsulta.add(actividad);
                }
                respuesta.setActividades(actividadConsulta);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    public static POJActividad obtenerActividadPorId(int idActividad) {
        POJActividad respuestaBD = new POJActividad();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT nombre, descripcion, fechaCreacion, fechaLimiteEntrega, estado FROM actividad WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    respuestaBD.setNombre(resultado.getString("nombre"));
                    respuestaBD.setDescripcion(resultado.getString("descripcion"));
                    respuestaBD.setFechaCreacion(resultado.getString("fechaCreacion"));
                    respuestaBD.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    respuestaBD.setEstado(resultado.getString("estado"));
                }
            } catch (SQLException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }

    public static int modificarActividad(POJActividad actividadModificar) {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "UPDATE actividad SET nombre = ?, descripcion = ?, "
                        + "fechaLimiteEntrega = ? WHERE  idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividadModificar.getNombre());
                prepararSentencia.setString(2, actividadModificar.getDescripcion());
                prepararSentencia.setString(3, actividadModificar.getFechaLimiteEntrega());
                prepararSentencia.setInt(4, actividadModificar.getIdActividad());
                System.err.println("Nombre: " + actividadModificar.getNombre());
                System.err.println("Descripción: " + actividadModificar.getDescripcion());
                System.err.println("Fecha límite: " + actividadModificar.getFechaLimiteEntrega());
                System.err.println("idActividad: " + actividadModificar.getIdActividad());
                prepararSentencia.executeUpdate();
                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;
    }

}
