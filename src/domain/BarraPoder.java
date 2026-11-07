package domain;

public class BarraPoder {
    private static final double TIEMPO_CARGA = 10;
    private static final double BONO_POR_GOL = TIEMPO_CARGA / 4;
    private double segundosCargados;

    public BarraPoder(){
        this.segundosCargados = 0;
    }

    public void actualizar(double segundos){
        segundosCargados = Math.min(segundosCargados + segundos, TIEMPO_CARGA);
    }

    public void recibirGol(){
        segundosCargados = Math.min(segundosCargados + BONO_POR_GOL, TIEMPO_CARGA);
    }

    public boolean estaLlena(){
        return segundosCargados >= TIEMPO_CARGA;
    }

    public void vaciar(){
        segundosCargados = 0;
    }

    public double getProgreso(){
        return segundosCargados / TIEMPO_CARGA;
    }

}
