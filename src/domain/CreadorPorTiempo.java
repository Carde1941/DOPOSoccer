package domain;

import java.util.List;

/** Crea partidos que duran cierta cantidad de minutos. */
public class CreadorPorTiempo implements CreadorTipoPartido {
    @Override
    public TipoPartido crear(int minutos) throws DOPOSoccerException {
        return new PorTiempo(minutos);
    }

    @Override
    public List<Integer> getCantidades() {
        return List.of(1, 2, 3, 5);
    }

    @Override
    public String describir(int minutos) {
        return minutos + (minutos == 1 ? " minuto" : " minutos");
    }
}