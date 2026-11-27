package domain;
import java.io.Serializable;

/** Factory Method: cada personaje crea su propio tiro de poder y el efecto que le deja al rival. */
public abstract class Personaje implements Serializable{

    public abstract String getNombre();

    public abstract double getDanoTiro();

    public abstract String getNombrePoder();

    public abstract Tiro crearTiroPoder();

    public abstract Efecto crearEfecto();
}
