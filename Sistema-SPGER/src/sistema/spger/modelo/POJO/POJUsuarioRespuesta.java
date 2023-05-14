package sistema.spger.modelo.POJO;

import java.util.ArrayList;


public class POJUsuarioRespuesta {
    private boolean usuarioDuplicado;
    private int codigoRespuesta;
    private ArrayList<POJUsuario> profesores;

    public boolean getUsuarioDuplicado() {
        return usuarioDuplicado;
    }

    public void setUsuarioDuplicado(boolean usuarioDuplicado) {
        this.usuarioDuplicado = usuarioDuplicado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJUsuario> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<POJUsuario> profesores) {
        this.profesores = profesores;
    }
}
