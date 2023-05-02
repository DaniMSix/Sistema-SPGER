package sistema.spger.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import sistema.spger.dbArchivos.DBAUsuarioBD;
import sistema.spger.dbArchivos.DBAObtenerUsuario;


public class ModConexionBD {
    private Connection connection;
    // jdbc:mysql://sistema-tutorias.mysql.database.azure.com:3306/sistematutoriasfei?useSSL=false
    // String url="jdbc:mysql://prueba-construccion.mysql.database.azure.com:3306/{your_database}?useSSL=true"
    private final String url="jdbc:mysql://prueba-construccion.mysql.database.azure.com:3306/spger?useSSL=false";
    private DBAUsuarioBD usuarioBD;

    public Connection getConnection() throws SQLException{
        connect();
        
        return connection;
    }

    private void connect() throws SQLException{
        usuarioBD = DBAObtenerUsuario.leerArchivoUsuario();
        connection=DriverManager.getConnection(url,usuarioBD.getUsuario(), usuarioBD.getContrasenia());
    }

    public void closeConection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    System.out.println("Éxito");
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error");
                //Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
