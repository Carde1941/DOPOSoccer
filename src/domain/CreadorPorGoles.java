package domain;

import java.util.List;

public class CreadorPorGoles implements CreadorTipoPartido {
    @Override
    public TipoPartido crear(int goles) throws DOPOSoccerException {
        return new PorGoles(goles);
    }

    @Override
    public List<Integer> getCantidades() {
        return List.of(1, 3, 5, 7);
    }

    @Override
    public String describir(int goles) {
        return goles + (goles == 1 ? " gol" : " goles");
    }
}