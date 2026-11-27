package domain;
import java.io.Serializable;

/**
 * Un personaje que se puede escoger. Cada uno dice cómo sale su tiro de poder y qué efecto le deja al
 * rival.
 */
public abstract class Personaje implements Serializable{

    /**
     * El nombre del personaje.
     *
     * @return el nombre, por ejemplo "Nigeria"
     */
    public abstract String getNombre();

    /**
     * Cuánta vida quita su tiro de poder, antes de sumarle la mejora de Kick.
     *
     * @return el daño
     */
    public abstract double getDanoTiro();

    /**
     * El nombre de su poder.
     *
     * @return el nombre, por ejemplo "Tornado"
     */
    public abstract String getNombrePoder();

    /**
     * Crea el tiro con el que sale el balón cuando lanza el poder.
     *
     * @return el tiro
     */
    public abstract Tiro crearTiroPoder();

    /**
     * Crea el efecto que le deja al rival si el poder lo golpea.
     *
     * @return el efecto
     */
    public abstract Efecto crearEfecto();
}
