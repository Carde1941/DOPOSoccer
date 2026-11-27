package domain;
import java.io.Serializable;

/** State: lo que le pasa a un jugador golpeado por un tiro de poder. Por defecto no le cambia nada. */
public abstract class Efecto implements Serializable{
    private final double duracion;
    private double segundosRestantes;

    public Efecto(double duracion){
        this.duracion = duracion;
        segundosRestantes = duracion;
    }

    public abstract String getNombre();

    public abstract String getDescripcion();

    public double getDuracion(){
        return duracion;
    }

    public void alEmpezar(Jugador afectado, Partido partido){
    }

    public void actualizar(double segundos){
        segundosRestantes = Math.max(segundosRestantes - segundos, 0);
    }

    public boolean haTerminado(){
        return segundosRestantes == 0;
    }

    public boolean puedeMoverse(){
        return true;
    }

    public boolean puedeSaltar(){
        return true;
    }

    public boolean puedePatear(){
        return true;
    }

    public double getFactorVelocidad(){
        return 1;
    }

    /** Velocidad horizontal que el efecto le impone al jugador, en píxeles por segundo. */
    public double getArrastre(){
        return 0;
    }

    public double getSegundosRestantes(){
        return segundosRestantes;
    }
}
