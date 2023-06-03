package sistema.spger.modelo.POJO;

public class POJLGAC{
    private int idLGAC;
    private String numeroLinea;
    private String nombre;

    public POJLGAC() {
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public void setNumeroLinea(String numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public String getNumeroLinea() {
        return numeroLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public POJLGAC(int idLGAC, String numeroLinea, String nombre, String descripcion) {
        this.idLGAC = idLGAC;
        this.numeroLinea = numeroLinea;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    private String descripcion;

    public POJLGAC(String numeroLinea, String nombre, String descripcion) {
        this.numeroLinea = numeroLinea;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public POJLGAC(int idLGAC, String nombre) {
        this.idLGAC = idLGAC;
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }
}
