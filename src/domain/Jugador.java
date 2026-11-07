package domain;

public class Jugador {
    private static final double DANO_BASE_PATADA = 10.0;
    private static final double DURACION_ATURDIMIENTO = 3.0;
    private Estadisticas estadisticas;
    private BarraPoder barra;
    private double vida;
    private int goles, puntos;
    private double tiempoAturdido;

    public Jugador(Estadisticas estadisticas){
        this.estadisticas = estadisticas;
        barra = new BarraPoder();
        vida = estadisticas.vidaMaxima();
        goles = 0;
        puntos = 0;
        tiempoAturdido = 0;
    }

    public double danoPatada(){
        return DANO_BASE_PATADA * estadisticas.multiplicadorKick();
    }

    public void recibirDano(double dano){
        if(estaAturdido()){
            return;
        } else{
            vida = Math.max(vida - dano, 0);
            if(vida == 0){
                tiempoAturdido = DURACION_ATURDIMIENTO;
            }
        }

    }

    public boolean estaAturdido(){
        return tiempoAturdido > 0;
    }

    public void actualizar(double segundos){
        barra.actualizar(segundos);
        if(estaAturdido()){
            tiempoAturdido -= segundos;
            if(tiempoAturdido <= 0){
                tiempoAturdido = 0;
                vida = estadisticas.vidaMaxima();
            }
        }
    }

    public void anotarGol(){
        goles += 1;
    }

    public void sumarPuntos(int cantidad){
        puntos += cantidad;
    }

    public void recibirGol(){
        barra.recibirGol();
    }

    public double getVida(){
        return vida;
    }

    public int getGoles(){
        return goles;
    }

    public int getPuntos(){
        return puntos;
    }

    public BarraPoder getBarra(){
        return barra;
    }

    public Estadisticas getEstadisticas(){
        return estadisticas;
    }
}
