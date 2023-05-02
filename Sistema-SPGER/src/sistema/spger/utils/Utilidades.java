package sistema.spger.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import jdk.nashorn.internal.objects.NativeDebug;
import sistema.spger.SistemaSPGER;


public class Utilidades {
    public static void mostrarDialogoSimple(String titulo, String mensaje,
            Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
    }
    
    public static Scene inicializarEscena(String ruta) {
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
}
