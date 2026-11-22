package domain;

/**
 * Decide según el estado del partido: defiende si va ganando por 2 goles o si el rival la amenaza con su poder,
 * y si no, ataca. Guarda el poder hasta que el balón está en la mitad del rival.
 */
public class Experta extends Maquina{
    private static final int VENTAJA_PARA_DEFENDER = 2;
    private final Maquina defensiva = new Defensiva();

    @Override
    protected void jugar(Jugador propio, Partido partido){
        Jugador rival = partido.rivalDe(propio);
        if (debeDefender(propio, rival, partido)){
            defensiva.jugar(propio, partido);
        } else{
            if (!balonEnSuMitad(propio, partido)){
                propio.activarPoder();
            }
            atacar(propio, partido);
        }
    }

    private boolean debeDefender(Jugador propio, Jugador rival, Partido partido){
        Balon balon = partido.getBalon();
        boolean amenazada = rival.tieneAura() || (balon.tienePoder() && balon.getPoder().esDe(rival));
        boolean ventajaComoda = propio.getGoles() - rival.getGoles() >= VENTAJA_PARA_DEFENDER;
        return amenazada || ventajaComoda;
    }
}
