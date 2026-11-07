package domain;

public class Balon {
    private static final double RADIO = 22.0;
    private static final double REBOTE = 0.75;
    private double x, y, vx, vy;

    public Balon(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }

    public void aplicarImpulso(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void reiniciar(double x, double y){
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
    }

    public void actualizar(double segundos, Cancha cancha){
        vy += cancha.getGravedad() * segundos;
        x += vx * segundos;
        y += vy * segundos;

        if(y + RADIO > cancha.getSuelo()){
            y = cancha.getSuelo() - RADIO;
            vy = -vy * REBOTE;
        }

        if(y - RADIO < 0){
            y = RADIO;
            vy = -vy * REBOTE;
        }

        if(x - RADIO < 0){
            x = RADIO;
            vx = -vx * REBOTE;
        }

        if(x + RADIO > cancha.getAncho()){
            x = cancha.getAncho() - RADIO;
            vx = -vx * REBOTE;
        }
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getVx(){
        return vx;
    }

    public double getVy(){
        return vy;
    }

    public double getRadio(){
        return RADIO;
    }

    public double getRebote(){
        return REBOTE;
    }
}
