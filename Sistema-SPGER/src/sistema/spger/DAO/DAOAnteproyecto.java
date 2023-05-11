package sistema.spger.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJAnteproyecto;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistema.spger.modelo.POJO.POJAnteproyectoRespuesta;
import sistema.spger.modelo.POJO.POJLGAC;
import sistema.spger.modelo.POJO.POJLGACRespuesta;
import sistema.spger.utils.Constantes;
public class DAOAnteproyecto {
    public static int guardarAnteproyecto(POJAnteproyecto anteproyectoNuevo) throws SQLException, FileNotFoundException{
        int respuesta;
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD !=null){
            try{
                String sentencia = "INSERT INTO anteproyecto(nombre,modalidad,DirectorDeTrabajo_idDirectorDeTrabajo,archivoAnteproyecto,LGAC_idLGAC,Estado_idEstado) "+
                        "VALUES (?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoNuevo.getNombreAnteproyecto());
                prepararSentencia.setString(2, anteproyectoNuevo.getModalidad());
                prepararSentencia.setString(3, "1");////////////////////////////////////
                prepararSentencia.setBytes(4,anteproyectoNuevo.getArchivoAnteproyecto());
                prepararSentencia.setInt(5, anteproyectoNuevo.getLGAC());
                prepararSentencia.setString(6, "1");///////////////////////////////////
                int filasAfectadas = prepararSentencia.executeUpdate();
                prepararSentencia.close();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA:Constantes.ERROR_CONSULTA;
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
                
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
            public static POJAnteproyectoRespuesta obtenerInformacionAnteproyecto() throws SQLException{
        POJAnteproyectoRespuesta respuesta = new POJAnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        System.out.println("antes");
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyecto.nombre,"+
                                  " modalidad, archivoAnteproyecto, usuario.nombre AS nombre_director, LGAC_idLGAC, estado.descripcion AS Estado_anteproyecto " +
                                  "FROM anteproyecto INNER JOIN directordetrabajo ON anteproyecto.DirectorDeTrabajo_idDirectorDeTrabajo = directordetrabajo.idDirectorDeTrabajo "+
                                  "INNER JOIN usuario ON directordetrabajo.Usuario_idUsuario = usuario.idUsuario "+
                                  "INNER JOIN estado ON anteproyecto.Estado_idEstado = estado.idEstado";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<POJAnteproyecto> anteproyectos = new ArrayList();
                while(resultado.next()){
                    POJAnteproyecto anteproyecto = new POJAnteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(resultado.getString("nombre"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setArchivoAnteproyecto(resultado.getBytes("archivoAnteproyecto"));                 
                    anteproyecto.setNombreDirectorDeTrabajo(resultado.getString("nombre_director"));
                    anteproyecto.setLGAC(resultado.getInt("LGAC_idLGAC"));
                    anteproyecto.setEstado(resultado.getString("Estado_anteproyecto"));
                    anteproyectos.add(anteproyecto);
                    System.out.println("Nombre anteproyecto"+anteproyecto.getNombreAnteproyecto()+"\nestado"+anteproyecto.getEstado());
                }
                respuesta.setAnteproyectos(anteproyectos);
                conexionBD.close();
                
            } catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
