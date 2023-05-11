package sistema.spger.modelo.POJO;

import java.util.ArrayList;

public class POJAnteproyectoRespuesta {
    int codigoRespuesta;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<POJAnteproyecto> getAnteproyectos() {
        return anteproyectos;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setAnteproyectos(ArrayList<POJAnteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }

    public POJAnteproyectoRespuesta(ArrayList<POJAnteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }

    public POJAnteproyectoRespuesta(int codigoRespuesta, ArrayList<POJAnteproyecto> anteproyectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectos = anteproyectos;
    }

    public POJAnteproyectoRespuesta() {
    }
    private ArrayList<POJAnteproyecto> anteproyectos;
}
