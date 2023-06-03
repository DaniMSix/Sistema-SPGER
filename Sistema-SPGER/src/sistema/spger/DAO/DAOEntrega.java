package sistema.spger.DAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJActividadEntregaRespuesta;
import sistema.spger.modelo.POJO.POJArchivos;
import sistema.spger.modelo.POJO.POJArchivosRespuesta;
import sistema.spger.modelo.POJO.POJEntrega;
import sistema.spger.utils.Constantes;


public class DAOEntrega {
    
    public static int registrarArchivosEntrega(POJArchivosRespuesta archivosInformacion) {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "INSERT INTO archivos (archivos, Entrega_idEntrega) VALUES (?,?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                for (POJArchivos archivoEntrega : archivosInformacion.getArchivosEntrega()) {
                    File archivo = archivoEntrega.getArchivosEntrega();
                    byte[] contenidoArchivo = Files.readAllBytes(archivo.toPath());

                    prepararSentencia.setBytes(1, contenidoArchivo);
                    prepararSentencia.setInt(2, archivoEntrega.getEntrega_idEntrega());
                    prepararSentencia.executeUpdate();
                }

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException | IOException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }

    public static int registrarEntrega(POJEntrega entregaInformacion) {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "INSERT INTO entrega (comentariosAlumno, fechaEntrega, Actividad_idActividad) "
                        + "VALUES (?, ?, ?);";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                prepararSentencia.setString(1, entregaInformacion.getComentariosAlumno());
                prepararSentencia.setString(2, entregaInformacion.getFechaEntrega());
                prepararSentencia.setInt(3, entregaInformacion.getActividad_idActividad());
                prepararSentencia.executeUpdate();

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }
    
    public static int actualizarEstadoActividad(String estadoActividad, int idActividad) {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "UPDATE actividad SET estado = ? WHERE idActividad = ?;";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                    prepararSentencia.setString(1, estadoActividad);
                    prepararSentencia.setInt(2, idActividad);
                    prepararSentencia.executeUpdate();

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }

    public static POJActividadEntregaRespuesta obtenerEntregaActividades(int idEstudiante, int idCurso){
        POJActividadEntregaRespuesta respuestaBD = new POJActividadEntregaRespuesta();
        ArrayList<POJActividadEntrega> actividadConsulta = new ArrayList();
        
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if(conexion != null){
            try{
                String consulta = "SELECT ca.idCurso, ca.idActividad, a.nombre, a.descripcion, a.estado, a.fechaLimiteEntrega, " +
                "e.calificacion, e.observacionProfesor, e.comentariosAlumno, e.fechaEntrega " +
                "FROM curso_actividad ca " +
                "LEFT JOIN usuario_actividad ua ON ca.idActividad = ua.idActividad " +
                "LEFT JOIN actividad a ON a.idActividad = ua.idActividad " +
                "LEFT JOIN entrega e ON a.idActividad = e.actividad_idActividad " +
                "WHERE ua.idUsuario = ? AND ca.idCurso = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, idCurso);
                ResultSet resultado =  prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                while(resultado.next()){
                    POJActividadEntrega actividadEntrega = new POJActividadEntrega();
                    actividadEntrega.setIdCurso(resultado.getInt("idCurso"));
                    actividadEntrega.setIdActividad(resultado.getInt("idActividad"));
                    actividadEntrega.setNombre(resultado.getString("nombre"));
                    actividadEntrega.setDescripcion(resultado.getString("descripcion"));
                    actividadEntrega.setEstado(resultado.getString("estado"));
                    actividadEntrega.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    actividadEntrega.setCalificacion(resultado.getFloat("calificacion"));
                    actividadEntrega.setObservacionProfesor(resultado.getString("observacionProfesor"));
                    actividadEntrega.setComentariosAlumno(resultado.getString("comentariosAlumno"));
                    actividadEntrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    
                    System.err.println("idCurso" + actividadEntrega.getIdCurso());
                    System.err.println("idActividad" + actividadEntrega.getIdActividad());
                    System.err.println("nombre" + actividadEntrega.getNombre());
                    System.err.println("descripción" + actividadEntrega.getDescripcion());
                    System.err.println("estado" + actividadEntrega.getEstado());
                    System.err.println("fecha limite entrega" + actividadEntrega.getFechaLimiteEntrega());
                    System.err.println("calificacion" + actividadEntrega.getCalificacion());
                    System.err.println("observacionProfesor" + actividadEntrega.getObservacionProfesor());
                    System.err.println("comentariosAlumno" + actividadEntrega.getComentariosAlumno());
                    System.err.println("fechaEntrega" + actividadEntrega.getFechaEntrega());
                    
                    actividadConsulta.add(actividadEntrega);
                }
                respuestaBD.setActividadesEntregas(actividadConsulta);
            }catch (SQLException e){
               respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    
    public static POJEntrega obtenerIdEntrega(POJEntrega entregaRegistrada){
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        POJEntrega respuestaBD = new POJEntrega();
        
        if (conexion != null){
            try{
                String consulta = "SELECT idEntrega FROM entrega WHERE Actividad_idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, entregaRegistrada.getIdEntrega());
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    int idEntrega = resultado.getInt("idEntrega");
                    System.out.println("idUsuario DAO"+ idEntrega);
                    respuestaBD.setIdEntrega(idEntrega);
                }
            } catch (SQLException ex) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }

}
