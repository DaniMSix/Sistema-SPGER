package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJLGAC;
import sistema.spger.modelo.POJO.POJLGACRespuesta;
import sistema.spger.utils.Constantes;

public class DAOLGAC {
        public static POJLGACRespuesta obtenerInformacionLGAC() throws SQLException{
        //TO DO . DEFINICION DE OBJETO
        POJLGACRespuesta respuesta = new POJLGACRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idLGAC, nombre, descripcion " +
                                        "from lgac; ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<POJLGAC> LGACConsulta = new ArrayList();
                while(resultado.next()){
                    POJLGAC lgac = new POJLGAC();
                    lgac.setIdLGAC(resultado.getInt("idLGAC"));
                    lgac.setNombre(resultado.getString("nombre"));
                    lgac.setDescripcion(resultado.getString("descripcion"));
                    LGACConsulta.add(lgac);
                }
                respuesta.setLgac(LGACConsulta);
                conexionBD.close();
                
            } catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        System.out.println(" respuesta "+respuesta.getCodigoRespuesta());
        return respuesta;
    }
}
