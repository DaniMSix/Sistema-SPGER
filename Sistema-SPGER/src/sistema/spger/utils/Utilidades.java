package sistema.spger.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;
import sistema.spger.controladores.FXMLPantallaPrincipalController;
import sistema.spger.modelo.POJO.POJUsuario;


public class Utilidades {
    public static void mostrarDialogoSimple(String titulo, String mensaje,
        Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.show();
    }
    
    public static Scene inicializarEscena(String ruta) throws IOException {
        Scene escena = null;
        try
        {
            //Parent vista = FXMLLoader.load(JFXControlEscolar.class.getResource(ruta));
            Parent vista = FXMLLoader.load(SistemaSPGER.class.getResource(ruta));
            escena = new Scene(vista);
        } catch (IOException ex)
        {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return escena;
    }
    
    public static String encriptarContraseñaSHA512(String contrasenia) throws UnsupportedEncodingException {
        String contrasenaEncriptada = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(contrasenia.getBytes("utf8"));
            contrasenaEncriptada = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException nsaException) {
            //Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, nsaException);
        } catch (UnsupportedEncodingException ueException) {
            //Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, ueException);
        }
        return contrasenaEncriptada;
    }
    
    public static void recibirRolesDeUsuario(List<POJUsuario> listaRolesDeUsuario){
        for (int i=0; i<listaRolesDeUsuario.size(); i++){
            System.out.println(listaRolesDeUsuario.get(i).getRol());
        }
        
    }
    public static File seleccionarArchivo(){
            final FileChooser fc= new FileChooser();
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF","*.pdf"),
                    new FileChooser.ExtensionFilter("word","*.docx"));
            fc.setTitle("Seleccionar archivo");
            File file =fc.showOpenDialog(null);
        return file;
    }
    public static void descargarPDF(String nombreAnteproyecto,byte[] archivo) throws FileNotFoundException, IOException{
        String directorioActual = System.getProperty(nombreAnteproyecto);
        File archivoPdf = new File(directorioActual,nombreAnteproyecto+".pdf");
        FileOutputStream fos = new FileOutputStream(archivoPdf);
        Utilidades.mostrarDialogoSimple("NOTIFICAION","archivo guardado en "+archivoPdf.getAbsolutePath(),Alert.AlertType.INFORMATION);
        fos.write(archivo);
        System.out.println("archivo guardado en "+archivoPdf.getAbsolutePath());
        fos.close();        
    }

}
