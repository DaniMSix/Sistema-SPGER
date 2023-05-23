package sistema.spger.modelo.POJO;

import java.time.LocalDate;
import java.util.Date;


public class POJActividad {
    private int idActividad;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaLimiteEntrega;
    private int idEstudiante;

    public POJActividad() {
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLimiteEntrega() {
        return fechaLimiteEntrega;
    }

    public void setFechaLimiteEntrega(Date fechaLimiteEntrega) {
        this.fechaLimiteEntrega = fechaLimiteEntrega;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public POJActividad(int idActividad, String nombre, String descripcion, Date fechaCreacion, Date fechaLimiteEntrega, int idEstudiante) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimiteEntrega = fechaLimiteEntrega;
        this.idEstudiante = idEstudiante;
    }
    
    
    
}
