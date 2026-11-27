package domain;

/** Máquina que solo piensa en anotar: siempre va por el balón y usa el poder apenas lo tiene. */
public class Ofensiva extends Maquina{

    @Override
    protected void jugar(Jugador propio, Partido partido){
        propio.activarPoder();
        atacar(propio, partido);
    }
}
