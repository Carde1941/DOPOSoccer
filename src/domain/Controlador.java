package domain;

import java.io.Serializable;

public interface Controlador extends Serializable {
    void decidir(Jugador propio, Partido partido, double segundos);

    boolean esHumano();
}
