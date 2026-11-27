package domain;

/**
 * Máquina que cambia de plan según el partido: defiende si va ganando por dos goles o más, o si el
 * rival tiene el poder activado o ya lo lanzó. Si no, ataca y usa su poder cuando el balón está lejos
 * de su arco.
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
