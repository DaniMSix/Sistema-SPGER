package sistema.spger.modelo.POJO;

import java.util.ArrayList;

public class POJLGACRespuesta {
    int codigoRespuesta;
    private ArrayList<POJLGAC> lgac;

    public POJLGACRespuesta() {
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setLgac(ArrayList<POJLGAC> lgac) {
        this.lgac = lgac;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<POJLGAC> getLgac() {
        return lgac;
    }

    public POJLGACRespuesta(ArrayList<POJLGAC> lgac) {
        this.lgac = lgac;
    }
    public POJLGACRespuesta(int codigoRespuesta, ArrayList<POJLGAC> lgac) {
        this.codigoRespuesta = codigoRespuesta;
        this.lgac = lgac;
    }

}
