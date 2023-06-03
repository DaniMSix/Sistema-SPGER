package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJActividadEntregaRespuesta;
import sistema.spger.utils.Constantes;

public class DAOActividadEntrega {
    
    public static POJActividadEntregaRespuesta obtenerEntregaActividades(int idEstudiante, int idCurso) {
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

}
