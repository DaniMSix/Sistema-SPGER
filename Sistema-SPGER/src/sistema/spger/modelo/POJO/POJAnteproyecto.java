package sistema.spger.modelo.POJO;


public class POJAnteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String modalidad;
    private byte[] archivoAnteproyecto;
    private String nombreDirectorDeTrabajo;
    private int LGAC;
    private String estado;

    public POJAnteproyecto() {
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
