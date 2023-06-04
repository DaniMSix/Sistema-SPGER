package sistema.spger.modelo.POJO;

import java.util.ArrayList;

public class POJEstudianteRespuesta {
    private ArrayList<POJEstudiante> estudiantes;
    private int codigoRespuesta;

    public POJEstudianteRespuesta() {
    }

    public POJEstudianteRespuesta(ArrayList<POJEstudiante> estudiantes, int codigoRespuesta) {
        this.estudiantes = estudiantes;
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setEstudiantes(ArrayList<POJEstudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJEstudiante> getEstudiantes() {
        return estudiantes;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }
}
