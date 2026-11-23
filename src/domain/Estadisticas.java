package domain;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Estadisticas implements Serializable{
    public static final String SPEED = "Speed";
    public static final String KICK = "Kick";
    public static final String JUMP = "Jump";
    public static final String SURVIVAL = "Survival";
    private static final int MINIMO =  1;
    private static final int MAXIMO = 10;
    private final Map<String, Integer> niveles = new LinkedHashMap<>();

    public Estadisticas() {
        niveles.put(SPEED, MINIMO);
        niveles.put(KICK, MINIMO);
        niveles.put(JUMP, MINIMO);
        niveles.put(SURVIVAL, MINIMO);
    }

    public double multiplicadorSpeed(){
        return multiplicador(SPEED);
    }

    public double multiplicadorJump(){
        return multiplicador(JUMP);
    }

    public double multiplicadorKick() {
        return multiplicador(KICK);
    }

    public double vidaMaxima() {
        return 100 * multiplicador(SURVIVAL);
    }

    public void setSpeed(int valor)  throws DOPOSoccerException{
        poner(SPEED, valor);
    }

    public void setKick(int valor) throws DOPOSoccerException {
        poner(KICK, valor);
    }

    public void setJump(int valor) throws DOPOSoccerException {
        poner(JUMP, valor);
    }

    public void setSurvival(int valor) throws DOPOSoccerException {
        poner(SURVIVAL, valor);
    }

    public int getSpeed(){
        return niveles.get(SPEED);
    }

    public int getKick(){
        return niveles.get(KICK);
    }

    public int getJump(){
        return niveles.get(JUMP);
    }

    public int getSurvival(){
        return niveles.get(SURVIVAL);
    }

    public List<String> getNombres(){
        return new ArrayList<>(niveles.keySet());
    }

    public int getNivel(String nombre) throws DOPOSoccerException {
        Integer nivel = niveles.get(nombre);
        if (nivel == null) {
            throw new DOPOSoccerException(DOPOSoccerException.ESTADISTICA_DESCONOCIDA);
        }
        return nivel;
    }

    public boolean estaAlMaximo(String nombre) throws DOPOSoccerException {
        return getNivel(nombre) == MAXIMO;
    }

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
