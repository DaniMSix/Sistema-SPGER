package sistema.spger.modelo.POJO;

public class POJEstudiante {
    private String nombreEstudiante;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private int idUsuario;
    private int idEstudiante;
    private int idAnteproyecto;    

    public POJEstudiante() {
    }

    public POJEstudiante(String nombreEstudiante, String apellidoPaterno, String apellidoMaterno, String matricula, int idUsuario, int idAnteproyecto) {
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.idUsuario = idUsuario;
        this.idAnteproyecto = idAnteproyecto;
    }

    public POJEstudiante(String nombreEstudiante, String apellidoPaterno, String apellidoMaterno, String matricula) {
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
    }

    public POJEstudiante(String nombreEstudiante, String apellidoPaterno, String apellidoMaterno) {
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    @Override
    public String toString() {
        return  nombreEstudiante + " " + apellidoPaterno + " " + apellidoMaterno ;
    }

    public void setIdEstudiante(int aInt) {
        this.idEstudiante=aInt;
    }
    public int getIdEstudiante(){
        return idEstudiante;
    }
    
}
