package domain;
import java.io.Serializable;

/** El poder que lleva el balón después de un tiro de poder, hasta que golpea al rival o se acaba. */
public class TiroPoder implements Serializable{
    private static final double DURACION = 2.5;
    private Jugador lanzador;
    private double dano;
    private Efecto efecto;
    private double factorGravedad;
    private double segundosRestantes;

    public TiroPoder(Jugador lanzador, double dano, Efecto efecto, double factorGravedad){
        this.lanzador = lanzador;
        this.dano = dano;
        this.efecto = efecto;
        this.factorGravedad = factorGravedad;
        segundosRestantes = DURACION;
    }

    public void actualizar(double segundos){
        segundosRestantes = Math.max(segundosRestantes - segundos, 0);
    }

    public boolean haTerminado(){
        return segundosRestantes == 0;
    }

    public boolean esDe(Jugador jugador){
        return lanzador == jugador;
    }

    public Jugador getLanzador(){
        return lanzador;
    }

    public double getDano(){
        return dano;
    }

    public Efecto getEfecto(){
        return efecto;
    }

    public double getFactorGravedad(){
        return factorGravedad;
    }
}
