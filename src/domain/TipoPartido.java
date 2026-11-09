package domain;

public abstract class TipoPartido {
    public void actualizar(double segundos){
        // por defecto el tiempo no afecta el partido
    };

    public abstract boolean haTerminado(int golesIzquierdo, int golesDerecho);

    public abstract String getInformacion(int golesIzquierdo, int golesDerecho);
}