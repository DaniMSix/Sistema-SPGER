package sistema.spger.modelo.POJO;


public class POJCursoRespuesta {
    private boolean cursoDuplicado;
    private int codigoRespuesta;

    public POJCursoRespuesta() {
        
    }

    public boolean isCursoDuplicado() {
        return cursoDuplicado;
    }

    public void setCursoDuplicado(boolean cursoDuplicado) {
        this.cursoDuplicado = cursoDuplicado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public POJCursoRespuesta(boolean cursoDuplicado, int codigoRespuesta) {
        this.cursoDuplicado = cursoDuplicado;
        this.codigoRespuesta = codigoRespuesta;
    }
    
}
