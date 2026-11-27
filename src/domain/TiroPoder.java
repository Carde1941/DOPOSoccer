package domain;
import java.io.Serializable;

/**
 * Lo que lleva el balón después de un tiro de poder: quién lo lanzó, cuánto daño hace y qué efecto
 * deja. Dura unos segundos o hasta que golpee a alguien.
 */
public class TiroPoder implements Serializable{
    private static final double DURACION = 2.5;
    private Jugador lanzador;
    private double dano;
    private Efecto efecto;
    private double factorGravedad;
    private double segundosRestantes;

    /**
     * Crea el tiro de poder.
     *
     * @param lanzador quien lanzó el poder
     * @param dano cuánta vida le quita
     * @param efecto lo que le pasa al rival si lo golpea
     * @param factorGravedad cuánto le afecta la gravedad al balón mientras dura
     */
    public TiroPoder(Jugador lanzador, double dano, Efecto efecto, double factorGravedad){
        this.lanzador = lanzador;
        this.dano = dano;
        this.efecto = efecto;
        this.factorGravedad = factorGravedad;
        segundosRestantes = DURACION;
    }

    /**
     * Descuenta el tiempo que le queda.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos){
        segundosRestantes = Math.max(segundosRestantes - segundos, 0);
    }

    /**
     * Dice si el poder ya se acabó.
     *
     * @return true si se acabó
     */
    public boolean haTerminado(){
        return segundosRestantes == 0;
    }

    /**
     * Dice si el poder lo lanzó ese jugador.
     *
     * @param jugador el jugador
     * @return true si es suyo
     */
    public boolean esDe(Jugador jugador){
        return lanzador == jugador;
    }

    /**
     * Quien lanzó el poder.
     *
     * @return el jugador
     */
    public Jugador getLanzador(){
        return lanzador;
    }

    /**
     * Cuánta vida quita.
     *
     * @return el daño
     */
    public double getDano(){
        return dano;
    }

    /**
     * El efecto que le deja al rival.
     *
     * @return el efecto
     */
    public Efecto getEfecto(){
        return efecto;
    }

    /**
     * Cuánto le afecta la gravedad al balón mientras dura.
     *
     * @return 1 si cae normal, 0 si va recto
     */
    public double getFactorGravedad(){
        return factorGravedad;
    }
}
