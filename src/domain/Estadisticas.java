package domain;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Los niveles de un jugador en Speed, Kick, Jump y Survival, del 1 al 10. Cada nivel le suma un 10 % a
 * la velocidad, la patada, el salto o la vida.
 */
public class Estadisticas implements Serializable{
    /** Velocidad para caminar. */
    public static final String SPEED = "Speed";
    /** Fuerza de la patada y daño del poder. */
    public static final String KICK = "Kick";
    /** Altura del salto. */
    public static final String JUMP = "Jump";
    /** Vida máxima. */
    public static final String SURVIVAL = "Survival";
    private static final int MINIMO =  1;
    private static final int MAXIMO = 10;
    private final Map<String, Integer> niveles = new LinkedHashMap<>();

    /** Crea las estadísticas con todo en nivel 1. */
    public Estadisticas() {
        niveles.put(SPEED, MINIMO);
        niveles.put(KICK, MINIMO);
        niveles.put(JUMP, MINIMO);
        niveles.put(SURVIVAL, MINIMO);
    }

    /**
     * Por cuánto se multiplica la velocidad para caminar.
     *
     * @return 1.0 en nivel 1, 1.1 en nivel 2, y así
     */
    public double multiplicadorSpeed(){
        return multiplicador(SPEED);
    }

    /**
     * Por cuánto se multiplica la altura del salto.
     *
     * @return 1.0 en nivel 1, 1.1 en nivel 2, y así
     */
    public double multiplicadorJump(){
        return multiplicador(JUMP);
    }

    /**
     * Por cuánto se multiplican la fuerza y el daño de las patadas.
     *
     * @return 1.0 en nivel 1, 1.1 en nivel 2, y así
     */
    public double multiplicadorKick() {
        return multiplicador(KICK);
    }

    /**
     * La vida con la que arranca el jugador.
     *
     * @return 100 en nivel 1, y un 10 % más por cada nivel de Survival
     */
    public double vidaMaxima() {
        return 100 * multiplicador(SURVIVAL);
    }

    /**
     * Cambia el nivel de Speed.
     *
     * @param valor el nivel nuevo, de 1 a 10
     * @throws DOPOSoccerException si el nivel no está entre 1 y 10
     */
    public void setSpeed(int valor)  throws DOPOSoccerException{
        poner(SPEED, valor);
    }

    /**
     * Cambia el nivel de Kick.
     *
     * @param valor el nivel nuevo, de 1 a 10
     * @throws DOPOSoccerException si el nivel no está entre 1 y 10
     */
    public void setKick(int valor) throws DOPOSoccerException {
        poner(KICK, valor);
    }

    /**
     * Cambia el nivel de Jump.
     *
     * @param valor el nivel nuevo, de 1 a 10
     * @throws DOPOSoccerException si el nivel no está entre 1 y 10
     */
    public void setJump(int valor) throws DOPOSoccerException {
        poner(JUMP, valor);
    }

    /**
     * Cambia el nivel de Survival.
     *
     * @param valor el nivel nuevo, de 1 a 10
     * @throws DOPOSoccerException si el nivel no está entre 1 y 10
     */
    public void setSurvival(int valor) throws DOPOSoccerException {
        poner(SURVIVAL, valor);
    }

    /**
     * El nivel de Speed.
     *
     * @return el nivel
     */
    public int getSpeed(){
        return niveles.get(SPEED);
    }

    /**
     * El nivel de Kick.
     *
     * @return el nivel
     */
    public int getKick(){
        return niveles.get(KICK);
    }

    /**
     * El nivel de Jump.
     *
     * @return el nivel
     */
    public int getJump(){
        return niveles.get(JUMP);
    }

    /**
     * El nivel de Survival.
     *
     * @return el nivel
     */
    public int getSurvival(){
        return niveles.get(SURVIVAL);
    }

    /**
     * Los nombres de las cuatro estadísticas, en orden.
     *
     * @return la lista de nombres
     */
    public List<String> getNombres(){
        return new ArrayList<>(niveles.keySet());
    }

    /**
     * El nivel de una estadística.
     *
     * @param nombre el nombre de la estadística
     * @return el nivel
     * @throws DOPOSoccerException si la estadística no existe
     */
    public int getNivel(String nombre) throws DOPOSoccerException {
        Integer nivel = niveles.get(nombre);
        if (nivel == null) {
            throw new DOPOSoccerException(DOPOSoccerException.ESTADISTICA_DESCONOCIDA);
        }
        return nivel;
    }

    /**
     * Dice si una estadística ya llegó al nivel 10.
     *
     * @param nombre el nombre de la estadística
     * @return true si ya no se puede subir
     * @throws DOPOSoccerException si la estadística no existe
     */
    public boolean estaAlMaximo(String nombre) throws DOPOSoccerException {
        return getNivel(nombre) == MAXIMO;
    }

    /**
     * Sube un nivel una estadística.
     *
     * @param nombre el nombre de la estadística
     * @throws DOPOSoccerException si no existe o ya está en 10
     */
    public void subir(String nombre) throws DOPOSoccerException {
        poner(nombre, getNivel(nombre) + 1);
    }

    private double multiplicador(String nombre){
        return 1 + 0.10 * (niveles.get(nombre) - 1);
    }

    private void poner(String nombre, int valor) throws DOPOSoccerException {
        validar(valor);
        niveles.put(nombre, valor);
    }

    private void validar(int valor) throws DOPOSoccerException {
        if (valor < MINIMO || valor > MAXIMO) {
            throw new DOPOSoccerException(DOPOSoccerException.ESTADISTICA_FUERA_DE_RANGO);
        }
    }
}
