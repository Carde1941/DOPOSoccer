package presentation;

import domain.DOPOSoccer;

public class Controles {
    private int jugador;
    private int izquierda;
    private int derecha;
    private int saltar;
    private int patear;
    private boolean izquierdaPresionada;
    private boolean derechaPresionada;

    public Controles(int jugador, int izquierda, int derecha, int saltar, int patear){
        this.jugador = jugador;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.saltar = saltar;
        this.patear = patear;
    }

    public void presionar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda){
            izquierdaPresionada = true;
            juego.moverIzquierda(jugador);
        } else if (tecla == derecha){
            derechaPresionada = true;
            juego.moverDerecha(jugador);
        } else if (tecla == saltar){
            juego.saltar(jugador);
        } else if (tecla == patear){
            juego.patear(jugador);
        }
    }

    public void soltar(int tecla, DOPOSoccer juego){
        if (tecla == izquierda){
            izquierdaPresionada = false;
        } else if (tecla == derecha){
            derechaPresionada = false;
        } else{
            return;
        }
        if (izquierdaPresionada){
            juego.moverIzquierda(jugador);
        } else if (derechaPresionada){
            juego.moverDerecha(jugador);
        } else{
            juego.detenerse(jugador);
        }
    }

    public void soltarTodo(DOPOSoccer juego){
        izquierdaPresionada = false;
        derechaPresionada = false;
        juego.detenerse(jugador);
    }

}
