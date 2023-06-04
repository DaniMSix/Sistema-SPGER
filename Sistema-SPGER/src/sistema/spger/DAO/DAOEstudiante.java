package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJEstudiante;
import sistema.spger.modelo.POJO.POJEstudianteRespuesta;
import sistema.spger.utils.Constantes;

public class DAOEstudiante {
    public static POJEstudianteRespuesta obtenerEstudiantesSinAnteproyecto() throws SQLException{
        POJEstudianteRespuesta respuestaBD = new POJEstudianteRespuesta();
        respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "select idEstudiante,idUsuario,Anteproyecto_idAnteproyecto,nombre,apellidoPaterno,apellidoMaterno,matricula \n" +
                        "from \n" +
                        "usuario,estudiante \n" +
                        "where \n" +
                        "Usuario_idUsuario=idUsuario and Anteproyecto_idAnteproyecto is null;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);;
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<POJEstudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    POJEstudiante estudiante = new POJEstudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setIdAnteproyecto(resultado.getInt("Anteproyecto_idAnteproyecto"));
                    estudiante.setNombreEstudiante(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setIdUsuario(resultado.getInt("idUsuario"));      
                    System.out.println(estudiante.getNombreEstudiante());
                    estudiantes.add(estudiante);
                }
                respuestaBD.setEstudiantes(estudiantes);
                conexionBD.close();
                
            } catch(SQLException e){
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    public static int asignarAnteproyecto(int idAnteproyecto,int idEstudiante) throws SQLException{
            int respuesta=Constantes.OPERACION_EXITOSA;
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
         if(conexionBD != null){
            try{
                String consulta = "UPDATE estudiante \n" +
                    "SET Anteproyecto_idAnteproyecto = ? \n" +
                    "WHERE idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                prepararSentencia.setInt(2, idEstudiante);
                prepararSentencia.executeUpdate();
                conexionBD.close();
            } catch(SQLException e){
               respuesta=Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta=Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
