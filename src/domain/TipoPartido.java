package domain;
import java.io.*;

public abstract class TipoPartido implements Serializable{
    public void actualizar(double segundos){
        // por defecto el tiempo no afecta el partido
    };

    public abstract boolean haTerminado(int golesIzquierdo, int golesDerecho);

    public abstract String getInformacion(int golesIzquierdo, int golesDerecho);
}