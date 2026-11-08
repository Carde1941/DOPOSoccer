package presentation;

import domain.DOPOSoccer;

public class Controles {
    private int jugador;
    private int izquierda;
    private int derecha;
    private int saltar;
    private int patear;

    public Controles(int jugador, int izquierda, int derecha, int saltar, int patear){
        this.jugador = jugador;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.saltar = saltar;
        this.patear = patear;
    }

    public void presionar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda){
            juego.moverIzquierda(jugador);
        } else if (tecla == derecha){
            juego.moverDerecha(jugador);
        } else if (tecla == saltar){
            juego.saltar(jugador);
        } else if (tecla == patear){
            juego.patear(jugador);
        }
    }

    public void soltar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda || tecla == derecha){
            juego.detenerse(jugador);
        }
    }


}
