package domain;
import java.io.Serializable;

/**
 * Lo que le pasa a un jugador cuando lo golpea un tiro de poder. El jugador le pregunta a su efecto si
 * puede moverse, saltar o patear. Por defecto no cambia nada; cada efecto cambia solo lo suyo.
 */
public abstract class Efecto implements Serializable{
    private final double duracion;
    private double segundosRestantes;

    /**
     * Crea el efecto con su duración.
     *
     * @param duracion cuántos segundos dura
     */
    public Efecto(double duracion){
        this.duracion = duracion;
        segundosRestantes = duracion;
    }

    /**
     * El nombre del efecto.
     *
     * @return el nombre, por ejemplo "Electrocutado"
     */
    public abstract String getNombre();

    /**
     * Explica en una frase qué le hace el efecto al jugador.
     *
     * @return la descripción
     */
    public abstract String getDescripcion();

    /**
     * Cuánto dura el efecto desde que empieza.
     *
     * @return la duración en segundos
     */
    public double getDuracion(){
        return duracion;
    }

    /**
     * Lo que pasa justo cuando el efecto le cae al jugador. Por defecto, nada.
     *
     * @param afectado el jugador que recibió el efecto
     * @param partido el partido que se está jugando
     */
    public void alEmpezar(Jugador afectado, Partido partido){
    }

    /**
     * Descuenta el tiempo que le queda al efecto.
     *
     * @param segundos cuánto tiempo pasó desde la última vez, en segundos
     */
    public void actualizar(double segundos){
        segundosRestantes = Math.max(segundosRestantes - segundos, 0);
    }

    /**
     * Dice si el efecto ya se acabó.
     *
     * @return true si ya se acabó
     */
    public boolean haTerminado(){
        return segundosRestantes == 0;
    }

    /**
     * Dice si el jugador puede caminar.
     *
     * @return true si puede
     */
    public boolean puedeMoverse(){
        return true;
    }

    /**
     * Dice si el jugador puede saltar.
     *
     * @return true si puede
     */
    public boolean puedeSaltar(){
        return true;
    }

    /**
     * Dice si el jugador puede patear.
     *
     * @return true si puede
     */
    public boolean puedePatear(){
        return true;
    }

    /**
     * Por cuánto se multiplica la velocidad del jugador.
     *
     * @return 1 si camina normal; 0.5 si va a la mitad
     */
    public double getFactorVelocidad(){
        return 1;
    }

    /**
     * Qué tan rápido arrastra el efecto al jugador hacia un lado, aunque él no quiera.
     *
     * @return la velocidad del arrastre; 0 si no lo arrastra
     */
    public double getArrastre(){
        return 0;
    }

    /**
     * Cuánto le queda al efecto.
     *
     * @return los segundos que faltan
     */
    public double getSegundosRestantes(){
        return segundosRestantes;
    }
}
