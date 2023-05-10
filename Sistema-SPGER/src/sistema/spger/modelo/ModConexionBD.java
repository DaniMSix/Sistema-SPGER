package sistema.spger.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import sistema.spger.dbArchivos.DBAUsuarioBD;
import sistema.spger.dbArchivos.DBAObtenerUsuario;
import sistema.spger.utils.Utilidades;


public class ModConexionBD {
    private Connection connection;
    private final String url="jdbc:mysql://prueba-construccion.mysql.database.azure.com:3306/spger?useSSL=false";
    private DBAUsuarioBD usuarioBD;

    public Connection getConnection() {
        connect();
        
        return connection;
    }

    private void connect() {
        usuarioBD = DBAObtenerUsuario.leerArchivoUsuario();
        try {
            connection = DriverManager.getConnection(url,usuarioBD.getUsuario(), usuarioBD.getContrasenia());
        } catch (SQLException ex) {
            System.err.println("Error de conexión con BD:"+ex.getMessage());
        }
       
    }

    public void closeConection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    try {
                        System.out.println("Éxito");
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ModConexionBD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
