package domain;

public class Estadisticas {
    private int speed;
    private int kick;
    private int jump;
    private int survival;
    private static final int MINIMO =  1;
    private static final int MAXIMO = 10;

    public Estadisticas() {
        this.speed = 1;
        this.kick = 1;
        this.jump = 1;
        this.survival = 1;
    }

    public double multiplicadorSpeed(){
        return 1 + 0.10 * (speed - 1);
    }

    public double multiplicadorJump(){
        return 1 + 0.10 * (jump - 1);
    }

    public double multiplicadorKick() {
        return 1 + 0.10 * (kick - 1);
    }

    public double vidaMaxima() {
        return 100 * (1 + 0.10 * (survival - 1));
    }

    public void setSpeed(int valor)  throws DOPOSoccerException{
        validar(valor);
        this.speed = valor;
    }

    public void setKick(int valor) throws DOPOSoccerException {
        validar(valor);
        this.kick=valor;

    }

    public void setJump(int valor) throws DOPOSoccerException {
        validar(valor);
        this.jump = valor;
    }

    public void setSurvival(int valor) throws DOPOSoccerException {
        validar(valor);
        this.survival = valor;
    }

    public int getSpeed(){
        return speed;
    }

    public int getKick(){
        return kick;
    }

    public int getJump(){
        return jump;
    }

    public int getSurvival(){
        return survival;
    }

    private void validar(int valor) throws DOPOSoccerException {
        if (valor < MINIMO || valor > MAXIMO) {
            throw new DOPOSoccerException(DOPOSoccerException.ESTADISTICA_FUERA_DE_RANGO);
        }
    }
}
