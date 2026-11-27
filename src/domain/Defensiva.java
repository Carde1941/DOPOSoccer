package domain;

/**
 * Máquina que cuida su arco: espera cerca de él y solo sale a buscar el balón cuando entra a su mitad
 * de la cancha.
 */
public class Defensiva extends Maquina{
    private static final double DISTANCIA_AL_ARCO = 200;

    @Override
    protected void jugar(Jugador propio, Partido partido){
        if (balonEnSuMitad(propio, partido)){
            propio.activarPoder();
            atacar(propio, partido);
        } else{
            irHacia(propio, posicionDeGuardia(propio, partido));
        }
    }

    private double posicionDeGuardia(Jugador propio, Partido partido){
        if (partido.sentidoDeAtaque(propio) == 1){
            return DISTANCIA_AL_ARCO;
        }
        return partido.getCancha().getAncho() - DISTANCIA_AL_ARCO;
    }
}
