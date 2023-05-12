package sistema.spger.modelo.POJO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import sistema.spger.utils.Utilidades;

public class POJAnteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String modalidad;
    private byte[] archivoAnteproyecto;
    private String nombreDirectorDeTrabajo;
    private int LGAC;
    private String estado;
    private Button boton = new Button("Descargar archivo");

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    public POJAnteproyecto() {
        boton.setOnMouseReleased(new EventHandler() {
	@Override
            public void handle(Event arg0) {
                System.out.println("Click en boton de "+nombreAnteproyecto);
            try {
                Utilidades.descargarPDF(nombreAnteproyecto,archivoAnteproyecto);
            } catch (IOException ex) {
                Logger.getLogger(POJAnteproyecto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
	});
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public void setNombreAnteproyecto(String nombreAnteproyecto) {
        this.nombreAnteproyecto = nombreAnteproyecto;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setArchivoAnteproyecto(byte[] archivoAnteproyecto) {
        this.archivoAnteproyecto = archivoAnteproyecto;
    }

    public void setNombreDirectorDeTrabajo(String nombreDirectorDeTrabajo) {
        this.nombreDirectorDeTrabajo = nombreDirectorDeTrabajo;
    }

    public void setLGAC(int LGAC) {
        this.LGAC = LGAC;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public String getNombreAnteproyecto() {
        return nombreAnteproyecto;
    }

    public String getModalidad() {
        return modalidad;
    }

    public byte[] getArchivoAnteproyecto() {
        return archivoAnteproyecto;
    }

    public String getNombreDirectorDeTrabajo() {
        return nombreDirectorDeTrabajo;
    }

    public int getLGAC() {
        return LGAC;
    }

    public String getEstado() {
        return estado;
    }

    public POJAnteproyecto(String nombreAnteproyecto, String modalidad, byte[] archivoAnteproyecto, String nombreDirectorDeTrabajo, int LGAC) {
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.modalidad = modalidad;
        this.archivoAnteproyecto = archivoAnteproyecto;
        this.nombreDirectorDeTrabajo = nombreDirectorDeTrabajo;
        this.LGAC = LGAC;
    }
}
